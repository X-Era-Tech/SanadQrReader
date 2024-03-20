package com.xera.sanadqrreader.ui.send_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.domain.models.OutStockProductEntity
import com.xera.sanadqrreader.domain.usecases.GetAllOutOfStockProducts
import com.xera.sanadqrreader.domain.usecases.GetAllOutStockLocal
import com.xera.sanadqrreader.domain.usecases.GetScannerForOutOfStockProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendScreenViewModel @Inject constructor(
    private val getScannerForOutOfStockProductsUseCase: GetScannerForOutOfStockProductsUseCase,
    private val getAllOutOfStockProducts: GetAllOutOfStockProducts,
    private val getAllOutStockLocal: GetAllOutStockLocal
) : ViewModel() {

    private val _state = MutableStateFlow(SendScreenUiState())
    val state = _state.asStateFlow()

    init {
        getAllOutProducts()
    }

    fun onToChanged(to: String) {
        _state.value = state.value.copy(
            toWarehouse = to
        )
    }

    fun starScanOutProducts() {
        viewModelScope.launch {
            try {
                getScannerForOutOfStockProductsUseCase.invoke(
                    to = _state.value.toWarehouse,
                    from = _state.value.fromWarehouse
                ).collect { result ->
                    if (result == "Scanning completed") {
                        getAllOutProducts()
                    }
                }
            }catch (e:Exception){
                _state.update {
                    it.copy(
                        error = e.message.toString() ?: "Duplicate QR code",
                    )
                }
            }
        }
    }


     private fun getAllOutProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val result = getAllOutOfStockProducts.invoke()
                _state.update {
                    it.copy(
                        details = result.map {it.toUiState() },
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                val local = getAllOutStockLocal.invoke()
                _state.update {
                    it.copy(
                        error = e.message.toString(),
                        isLoading = false,
                        details = local.map { it.toUiState() }
                    )
                }
            }
        }
    }

    fun writeProductsToExcel(context:Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllOutOfStockProducts.writeProductsToExcel(context,getAllOutOfStockProducts.invoke())
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message.toString()
                    )
                }
            }
        }
    }

    private fun OutStockProductEntity.toUiState(): OutProductsQrCode {
        return OutProductsQrCode(
            qrCode = this.qrCode,
            to = this.to,
            states = this.status,
            getOutTime = this.getOutTime,
            getInTime = this.getInTime
        )
    }
}
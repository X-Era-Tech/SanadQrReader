package com.xera.sanadqrreader.ui.receive_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.usecases.GetAllInStockLocal
import com.xera.sanadqrreader.domain.usecases.GetAllInStockProducts
import com.xera.sanadqrreader.domain.usecases.GetScannerForInStockProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val getScannerUseCase: GetScannerForInStockProductsUseCase,
    private val getAllQrCodes: GetAllInStockProducts,
    private val getAllInStockLocal: GetAllInStockLocal
) : ViewModel() {
    private val _state = MutableStateFlow(ScanScreenUiState())
    val state = _state.asStateFlow()

    init {
        getAllQrCodes()
    }


    fun onFromChanged(fromWarehouse: String) {
        _state.value = state.value.copy(
            fromWarehouse = fromWarehouse
        )
    }


    fun startScanning() {
        viewModelScope.launch {
            try {
                getScannerUseCase.invoke(
                    to = _state.value.toWarehouse,
                    from = _state.value.fromWarehouse
                ).collect { result ->
                    if (result == "Scanning completed") {
                        getAllQrCodes()
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
    private fun getAllQrCodes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(
                isLoading = true
            ) }
            try {
                val result = getAllQrCodes.invoke()
                _state.update {
                    it.copy(
                        details = result.map { it.toUiState() },
                        isLoading = false
                    )
                }
            }catch (e:Exception){
                val local = getAllInStockLocal.invoke()
                _state.update { it.copy(
                    error = e.message.toString(),
                    isLoading = false,
                    details = local.map { it.toUiState() }
                    ) }
            }
        }
    }

    fun writeProductsToExcel(context: Context) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            getAllQrCodes.writeProductsToExcel(context, getAllQrCodes.invoke())
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }


    private fun InStockEntity.toUiState(): QrCodes {
        return QrCodes(
            qrCode = this.qrCode,
            from = this.from,
            states = this.status,
            getInTime = this.getInTime,
            getOutTime = this.getOutTime
        )
    }
}


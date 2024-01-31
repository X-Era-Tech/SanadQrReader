package com.xera.sanadqrreader.ui.send_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.domain.usecases.GetAllOutOfStockProducts
import com.xera.sanadqrreader.domain.usecases.GetScannerForOutOfStockProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendScreenViewModel @Inject constructor(
    private val getScannerForOutOfStockProductsUseCase: GetScannerForOutOfStockProductsUseCase,
    private val getAllOutOfStockProducts: GetAllOutOfStockProducts
): ViewModel() {

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

    fun starScanOutProducts(){
        viewModelScope.launch {
            getScannerForOutOfStockProductsUseCase.invoke(
                to = _state.value.toWarehouse,
                from = _state.value.fromWarehouse
            ).collect { result ->
                if (result == "Scanning completed") {
                    getAllOutProducts()
                }
            }
        }
    }


    private fun getAllOutProducts() {
        viewModelScope.launch {
            val result = getAllOutOfStockProducts.invoke().map { it.toUiState() }
            _state.value = state.value.copy(
                details = result,
            )
        }
    }



    private fun OutStockProducts.toUiState(): OutProductsQrCode {
        return OutProductsQrCode(
            qrCode = this.qrCode,
            to = this.to,
            states = this.status,
            getOutTime = this.getOutTime,
            getInTime = this.getInTime
        )
    }
}
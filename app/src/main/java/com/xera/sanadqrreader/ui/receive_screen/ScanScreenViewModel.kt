package com.xera.sanadqrreader.ui.receive_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.usecases.GetAllInStockProducts
import com.xera.sanadqrreader.domain.usecases.GetScannerForInStockProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val getScannerUseCase: GetScannerForInStockProductsUseCase,
    private val getAllQrCodes: GetAllInStockProducts
) : ViewModel() {
    private val _state = MutableStateFlow(ScanScreenUiState())
    val state = _state.asStateFlow()

    init {
        getAllQrCodes()
    }

    fun onToChanged(to: String) {
        _state.value = state.value.copy(
            toWarehouse = to
        )
    }

    fun onFromChanged(fromWarehouse: String) {
        _state.value = state.value.copy(
            fromWarehouse = fromWarehouse
        )
    }


    fun startScanning() {
        viewModelScope.launch {
            getScannerUseCase.invoke(
                to = _state.value.toWarehouse,
                from = _state.value.fromWarehouse
            ).collect { result ->
                if (result == "Scanning completed") {
                    getAllQrCodes()
                }
            }
        }
    }
    private fun getAllQrCodes() {
        viewModelScope.launch {
            val result = getAllQrCodes.invoke().map { it.toUiState() }
            _state.value = state.value.copy(
                details = result,
            )
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


package com.xera.sanadqrreader.ui.scan_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xera.sanadqrreader.data.repository.entities.QrReaderDto
import com.xera.sanadqrreader.domain.usecases.GetAllQrCodesUseCase
import com.xera.sanadqrreader.domain.usecases.GetScannerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val getScannerUseCase: GetScannerUseCase,
    private val getAllQrCodes: GetAllQrCodesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ScanScreenUiState())
    val state = _state.asStateFlow()

    init {
        getAllQrCodes()
    }

    private fun getAllQrCodes() {
        viewModelScope.launch {
            val result = getAllQrCodes.invoke().map { it.toUiState() }
            _state.value = state.value.copy(
                details = result
            )
        }

    }

    fun startScanning() {
        viewModelScope.launch {
            getScannerUseCase.invoke().collect{
                if (it == "Scanning completed") {
                    getAllQrCodes()
                }
            }
        }
    }

    private fun QrReaderDto.toUiState():QrCodes {
        return QrCodes(
            qrCode = this.qrCode,
            states = this.status,
            getInTime = this.getInTime,
            getOutTime = this.getOutTime
        )
    }
}


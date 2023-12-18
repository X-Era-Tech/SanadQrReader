package com.xera.sanadqrreader.ui.scan_screen

data class ScanScreenUiState(
    val details : List<QrCodes> = emptyList(),
    val isLoading : Boolean = false
)

data class QrCodes(
    val qrCode : String?,
    val states : String?,
    val getInTime : String?,
    val getOutTime : String?

)

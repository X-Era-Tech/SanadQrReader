package com.xera.sanadqrreader.ui.receive_screen

data class ScanScreenUiState(
    val details : List<QrCodes> = emptyList(),
    val isLoading : Boolean = false,
    val toWarehouse : String = "Maadi",
    val fromWarehouse : String = "",
)

data class QrCodes(
    val qrCode : String?,
    val states : String?,
    val getInTime : String?,
    val getOutTime : String?,
    val from : String?
)

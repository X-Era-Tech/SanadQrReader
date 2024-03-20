package com.xera.sanadqrreader.ui.send_screen

data class SendScreenUiState(
    val details : List<OutProductsQrCode> = emptyList(),
    val isLoading : Boolean = false,
    val toWarehouse : String = "",
    val fromWarehouse : String = "Maadi",
    val error : String = ""
)

data class OutProductsQrCode(
    val qrCode : String?,
    val states : String?,
    val getOutTime : String?,
    val getInTime : String?,
    val to : String?
)

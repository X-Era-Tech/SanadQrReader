package com.xera.sanadqrreader.ui.product_history

data class ProductHistoryUiState(
    val details : List<History> = emptyList(),
    val isLoading : Boolean = false,
)


data class History(
    val qrCode : String?,
    val status : String?,
    val getInTime : String?,
    val getOutTime : String?,
    val from : String?,
    val to : String?
)
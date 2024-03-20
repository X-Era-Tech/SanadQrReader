package com.xera.sanadqrreader.data.repository.resources.in_stock_resource

data class AddProductInStockResource(
    val from: String,
    val getInTime: String,
    val getOutTime: String? = null,
    val qrCode: String,
    val status: String,
    val to: String
)
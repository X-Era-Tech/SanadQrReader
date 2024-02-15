package com.xera.sanadqrreader.data.repository.resources.in_stock_resource

data class UpdateInStockProductResource(
    val from: String,
    val getInTime: String,
    val getOutTime: String,
    val qrCode: String,
    val status: String,
    val to: String
)
package com.xera.sanadqrreader.data.repository.resources.out_stock_resource

data class GetAllOutStockResourceItem(
    val from: String,
    val getInTime: String,
    val getOutTime: String,
    val qrCode: String,
    val status: String,
    val to: String
)
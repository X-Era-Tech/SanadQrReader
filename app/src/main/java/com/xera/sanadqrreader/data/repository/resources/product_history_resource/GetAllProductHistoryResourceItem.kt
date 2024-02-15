package com.xera.sanadqrreader.data.repository.resources.product_history_resource

data class GetAllProductHistoryResourceItem(
    val from: String,
    val getInTime: String,
    val getOutTime: String,
    val id: Int,
    val qrCode: String,
    val status: String,
    val to: String
)
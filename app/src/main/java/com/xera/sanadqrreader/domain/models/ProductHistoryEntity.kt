package com.xera.sanadqrreader.domain.models

data class ProductHistoryEntity(
    val id: Int? = 0,
    val qrCode: String,
    val getInTime : String? = null,
    val getOutTime : String? = null,
    val to : String? = null,
    val from : String? = null,
    val status : String? = null,
)

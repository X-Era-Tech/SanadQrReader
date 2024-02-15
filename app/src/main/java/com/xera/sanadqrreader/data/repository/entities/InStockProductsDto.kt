package com.xera.sanadqrreader.data.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "In_Stock_Products")
data class InStockProductsDto (
    @PrimaryKey val qrCode: String,
    val getInTime : String? = null,
    val getOutTime : String? = null,
    val to : String? = null,
    val from : String? = null,
    val status : String? = null,
    )
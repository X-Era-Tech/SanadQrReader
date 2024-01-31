package com.xera.sanadqrreader.data.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Out_Stock_Table")
data class OutStockProducts(
    @PrimaryKey val qrCode: String,
    val getOutTime: String? = null,
    val getInTime : String? = null,
    val from: String? = null,
    val to: String? = null,
    val status: String? = null,
)

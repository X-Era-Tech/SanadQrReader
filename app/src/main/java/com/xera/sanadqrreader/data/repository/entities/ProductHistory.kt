package com.xera.sanadqrreader.data.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_history")
data class ProductHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val qrCode: String,
    val getInTime: String?,
    val getOutTime: String?,
    val to: String,
    val from: String,
    val status: String
)

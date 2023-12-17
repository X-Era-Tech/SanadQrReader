package com.xera.sanadqrreader.data.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Qr_Reader_Table")
data class QrReaderDto (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val qrCode: String? = null,
    val status : String? = null,
    )
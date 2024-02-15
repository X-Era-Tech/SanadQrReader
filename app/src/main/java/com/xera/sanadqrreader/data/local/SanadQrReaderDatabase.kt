package com.xera.sanadqrreader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xera.sanadqrreader.data.repository.entities.InStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.OutStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.ProductHistoryDto

@Database(
    entities = [InStockProductsDto::class,
        OutStockProductsDto::class,
        ProductHistoryDto::class
    ],
    version = 3,
    exportSchema = false
)
abstract class SanadQrReaderDatabase : RoomDatabase() {

    abstract fun sanadQrReaderDao(): SanadQrReaderDao
}
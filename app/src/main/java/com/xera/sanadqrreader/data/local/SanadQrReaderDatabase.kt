package com.xera.sanadqrreader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xera.sanadqrreader.data.repository.entities.InStockProducts
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.data.repository.entities.ProductHistory

@Database(
    entities = [InStockProducts::class,
        OutStockProducts::class,
        ProductHistory::class
    ],
    version = 3,
    exportSchema = false
)
abstract class SanadQrReaderDatabase : RoomDatabase() {

    abstract fun sanadQrReaderDao(): SanadQrReaderDao
}
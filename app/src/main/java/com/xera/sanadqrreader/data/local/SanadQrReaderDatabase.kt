package com.xera.sanadqrreader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xera.sanadqrreader.data.repository.entities.QrReaderDto

@Database(
    entities = [QrReaderDto::class],
    version = 1,
    exportSchema = false
)
abstract class SanadQrReaderDatabase: RoomDatabase() {

    abstract fun sanadQrReaderDao(): SanadQrReaderDao
}
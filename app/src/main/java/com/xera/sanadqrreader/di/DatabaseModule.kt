package com.xera.sanadqrreader.di

import android.content.Context
import androidx.room.Room
import com.xera.sanadqrreader.data.local.SanadQrReaderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSanadQrReaderDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            SanadQrReaderDatabase::class.java,
            "SanadQrReaderDatabase"
        ).build()


    @Singleton
    @Provides
    fun provideSanadQrReaderDao(sanadQrReaderDatabase: SanadQrReaderDatabase) = sanadQrReaderDatabase.sanadQrReaderDao()


}
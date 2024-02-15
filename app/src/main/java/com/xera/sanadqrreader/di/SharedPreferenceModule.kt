package com.xera.sanadqrreader.di

import android.content.Context
import android.content.SharedPreferences
import com.xera.sanadqrreader.data.local.SharedPrefImpl
import com.xera.sanadqrreader.data.repository.data_sources.SharedPreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideTinyStepsSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("Xera-SharedPref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPreferencesDataSource(sharedPrefImpl: SharedPrefImpl): SharedPreferenceDataSource {
        return sharedPrefImpl
    }
}


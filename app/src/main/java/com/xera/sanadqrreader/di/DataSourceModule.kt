package com.xera.sanadqrreader.di

import com.xera.sanadqrreader.data.repository.data_sources.LocalDataSource
import com.xera.sanadqrreader.data.local.LocalDataSourceImpl
import com.xera.sanadqrreader.data.remote.RemoteDataSourceImpl
import com.xera.sanadqrreader.data.repository.data_sources.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLocalDataSources(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl):RemoteDataSource
}
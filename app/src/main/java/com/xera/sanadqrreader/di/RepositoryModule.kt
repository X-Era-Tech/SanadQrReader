package com.xera.sanadqrreader.di

import com.xera.sanadqrreader.data.repository.ScannerRepositoryImpl
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun binScannerRepo(
        scannerRepositoryImpl: ScannerRepositoryImpl
    ):ScannerRepository
}
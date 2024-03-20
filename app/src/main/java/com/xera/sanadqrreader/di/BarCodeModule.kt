package com.xera.sanadqrreader.di

import android.app.Application
import android.content.Context
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BarCodeModule {


    @Provides
    @ViewModelScoped
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @ViewModelScoped
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_CODE_39,Barcode.FORMAT_CODE_128,Barcode.FORMAT_ITF,Barcode.FORMAT_PDF417)
            .build()
    }

    @Provides
    fun moduleInstallClient(context: Context): ModuleInstallClient {
        return ModuleInstall.getClient(context)
    }
    @Provides
    fun moduleInstallClientRequest(gmsBarcodeScanner: GmsBarcodeScanner): ModuleInstallRequest {
        return ModuleInstallRequest.newBuilder().addApi(gmsBarcodeScanner).build()
    }


    @Provides
    @ViewModelScoped
    fun provideScanner(context: Context, options: GmsBarcodeScannerOptions): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context,options)
    }
}
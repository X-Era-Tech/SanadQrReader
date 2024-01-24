package com.xera.sanadqrreader.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.xera.sanadqrreader.data.local.LocalDataSource
import com.xera.sanadqrreader.data.repository.entities.InStockProducts
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.data.repository.entities.ProductHistory
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.Instant
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,
    private val moduleInstallClient: ModuleInstallClient,
    private val moduleInstallClientRequest: ModuleInstallRequest,
    private val localDataSource: LocalDataSource,
) : ScannerRepository {

    private val scanningEnabled = MutableStateFlow(true)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startScanning(
        saveFunction: suspend (String?, String?, String, String) -> Unit,
        to: String,
        from: String
    ): Flow<String?> {
        return callbackFlow {
            val time = Time.from(Instant.now())
            try {
                moduleInstallClient
                    .installModules(moduleInstallClientRequest)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            fun startNewScan() {
                                if (scanningEnabled.value) {
                                    scanner.startScan()
                                        .addOnSuccessListener { barCode ->
                                            launch(Dispatchers.IO) {
                                                Log.i(
                                                    "Darkness",
                                                    "startScanning: ${barCode.rawValue}"
                                                )
                                                send(barCode.rawValue)
                                                barCode.rawValue?.let {
                                                    saveFunction(
                                                        it,
                                                        time.toString(), to, from
                                                    )
                                                }
                                                send("Scanning completed")
                                                startNewScan()
                                            }
                                        }
                                }
                            }

                            scanner.startScan()
                                .addOnSuccessListener { barCode ->
                                    launch(Dispatchers.IO) {
                                        Log.i("Darkness", "startScanning: ${barCode.rawValue}")
                                        send(barCode.rawValue)
                                        barCode.rawValue?.let {
                                            saveFunction(
                                                it,
                                                time.toString(),
                                                to,
                                                from
                                            )
                                        }
                                        send("Scanning completed")

                                        startNewScan()
                                    }
                                }
                                .addOnFailureListener {
                                    it.printStackTrace()

                                    startNewScan()
                                }
                        } else {
                            Log.i("Darkness", "startScanning: ${task.exception}")
                            task.exception?.printStackTrace()
                        }
                    }
                    .addOnCanceledListener {
                        scanningEnabled.value = false
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            awaitClose { }
        }
    }

    override suspend fun saveInStockProduct(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    ) {
        if (!localDataSource.isProductInStock(qrCode)) {
            localDataSource.saveInStockProduct(qrCode, getInTime, to, from, "in", getOutTime)
        }
    }


    override suspend fun updateProductStatus(qrCode: String, getOutTime: String) {
        localDataSource.updateProductStatus(qrCode = qrCode, getOutTime =  getOutTime)
    }

    override suspend fun getAllInStockProduct(): List<InStockProducts> {
        return localDataSource.getAllInStockProducts()
    }

    override suspend fun getAllOutOfStockProducts(): List<OutStockProducts> {
        return localDataSource.getAllOutStockProducts()
    }

    override suspend fun saveOutStockProduct(
        qrCode: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String,
        getInTime: String
    ) {
        if (!localDataSource.isProductOutStock(qrCode)) {
            localDataSource.saveOutStockProduct(qrCode, getOutTime, to, from, "out",getInTime)
        }
    }

    override suspend fun deleteInStockProduct(qrCode: String) {
        localDataSource.deleteInStockProduct(qrCode)
    }

    override suspend fun isProductInStock(qrCode: String): Boolean {
        return localDataSource.isProductInStock(qrCode)
    }

    override suspend fun isProductOutStock(qrCode: String): Boolean {
        return localDataSource.isProductOutStock(qrCode)
    }

    override suspend fun deleteOutStockProduct(qrCode: String) {
        localDataSource.deleteOutStockProduct(qrCode)
    }

    override suspend fun getInTimeForProduct(qrCode: String): String {
        return localDataSource.getInTime(qrCode)
    }

    override suspend fun getOutTimeForProduct(qrCode: String): String {
        return localDataSource.getOutTime(qrCode)
    }

    override suspend fun getProductHistory(qrCode: String): List<ProductHistory> {
        return localDataSource.getProductHistory(qrCode)
    }

    override suspend fun insertProductHistory(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    ) {
        localDataSource.saveProductHistory(qrCode, getInTime, getOutTime, to, from, status)
    }


}
package com.xera.sanadqrreader.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.xera.sanadqrreader.data.local.LocalDataSource
import com.xera.sanadqrreader.data.repository.entities.QrReaderDto
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
    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            val time = Time.from(Instant.now())
            try {
                moduleInstallClient
                    .installModules(moduleInstallClientRequest)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Function to start a new scan
                            fun startNewScan() {
                                if (scanningEnabled.value) {
                                    scanner.startScan()
                                        .addOnSuccessListener { barCode ->
                                            launch(Dispatchers.IO) {
                                                Log.i("Darkness", "startScanning: ${barCode.rawValue}")
                                                send(barCode.rawValue)
                                                barCode.rawValue?.let { saveScannedQrCode(it, time.toString()) }
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
                                        barCode.rawValue?.let { saveScannedQrCode(it, time.toString()) }
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

    override suspend fun saveScannedQrCode(qrCode: String, getInTime: String) {
        if (localDataSource.isQrCodeExists(qrCode)) {
            localDataSource.updateQrCodeState(qrCode, "out of stock", getInTime)
        } else {
            localDataSource.saveScannedQrCode(qrCode, getInTime)
        }
    }

    override suspend fun updateQrCodeState(qrCode: String, status: String, getOutTime: String) {
        localDataSource.updateQrCodeState(qrCode, status, getOutTime)
    }

    override suspend fun getAllInStockQrCodes(): List<QrReaderDto> {
        return localDataSource.getAllInStockQrCodes()
    }

    override suspend fun getAllOutOfStockQrCodes(): List<QrReaderDto> {
        return localDataSource.getAllOutOfStockQrCodes()
    }

    override suspend fun getAllQrCodes(): List<QrReaderDto> {
        return localDataSource.getAllQrCodes()
    }


}
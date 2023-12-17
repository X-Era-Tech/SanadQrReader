package com.xera.sanadqrreader.data.repository

import android.util.Log
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.xera.sanadqrreader.data.local.LocalDataSource
import com.xera.sanadqrreader.data.repository.entities.QrReaderDto
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,
    private val moduleInstallClient: ModuleInstallClient,
    private val moduleInstallClientRequest: ModuleInstallRequest,
    private val localDataSource: LocalDataSource,
) : ScannerRepository {

    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            try {
                moduleInstallClient
                    .installModules(moduleInstallClientRequest).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            scanner.startScan()
                                .addOnSuccessListener { barCode ->
                                    launch(Dispatchers.IO) {
                                        Log.i("Darkness", "startScanning: ${barCode.rawValue}")
                                        send(barCode.rawValue)
                                        barCode.rawValue?.let { saveScannedQrCode(it) }
                                        send("Scanning completed")
                                    }
                                }
                                .addOnFailureListener {
                                    it.printStackTrace()
                                }
                        } else {
                            Log.i("Darkness", "startScanning: ${task.exception}")
                            task.exception?.printStackTrace()
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            awaitClose { }
        }
    }

    override suspend fun saveScannedQrCode(qrCode: String) {
        if (localDataSource.isQrCodeExists(qrCode)) {
            localDataSource.updateQrCodeState(qrCode, "out of stock")
        } else {
            localDataSource.saveScannedQrCode(qrCode)
        }
    }

    override suspend fun updateQrCodeState(qrCode: String, status: String) {
        localDataSource.updateQrCodeState(qrCode, status)
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
    /*
        private fun getBarCodeDetails(barcode: Barcode): String {
            return when (barcode.valueType) {
                Barcode.TYPE_WIFI -> {
                    val ssid = barcode.wifi!!.ssid
                    val password = barcode.wifi!!.password
                    val type = barcode.wifi!!.encryptionType
                    "ssid : $ssid, password : $password, type : $type"
                }

                Barcode.TYPE_URL -> {
                    "url : ${barcode.url!!.url}"
                }

                Barcode.TYPE_PRODUCT -> {
                    "productType : ${barcode.displayValue}"
                }

                Barcode.TYPE_EMAIL -> {
                    "email : ${barcode.email}"
                }

                Barcode.TYPE_CONTACT_INFO -> {
                    "contact : ${barcode.contactInfo}"
                }

                Barcode.TYPE_PHONE -> {
                    "phone : ${barcode.phone}"
                }

                Barcode.TYPE_CALENDAR_EVENT -> {
                    "calender event : ${barcode.calendarEvent}"
                }

                Barcode.TYPE_GEO -> {
                    "geo point : ${barcode.geoPoint}"
                }

                Barcode.TYPE_ISBN -> {
                    "isbn : ${barcode.displayValue}"
                }

                Barcode.TYPE_DRIVER_LICENSE -> {
                    "driving license : ${barcode.driverLicense}"
                }

                Barcode.TYPE_SMS -> {
                    "sms : ${barcode.sms}"
                }

                Barcode.TYPE_TEXT -> {
                    "text : ${barcode.rawValue}"
                }

                Barcode.TYPE_UNKNOWN -> {
                    "unknown : ${barcode.rawValue}"
                }

                else -> {
                    "Couldn't determine"
                }
            }
        }
     */
}
package com.xera.sanadqrreader.domain.repository

import com.xera.sanadqrreader.data.repository.entities.QrReaderDto
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {

    fun startScanning () : Flow<String?>

    suspend fun saveScannedQrCode(qrCode: String,getInTime:String)

    suspend fun updateQrCodeState(qrCode: String, status: String, getOutTime:String)

    suspend fun getAllInStockQrCodes(): List<QrReaderDto>

    suspend fun getAllOutOfStockQrCodes(): List<QrReaderDto>

    suspend fun getAllQrCodes(): List<QrReaderDto>
}
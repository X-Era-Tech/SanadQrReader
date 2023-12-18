package com.xera.sanadqrreader.data.local

import com.xera.sanadqrreader.data.repository.entities.QrReaderDto

interface LocalDataSource {


    suspend fun saveScannedQrCode(qrCode: String,getInTime:String)

    suspend fun updateQrCodeState(qrCode: String, status: String, getOutTime:String)

    suspend fun isQrCodeExists(qrCode: String): Boolean

    suspend fun getAllInStockQrCodes(): List<QrReaderDto>

    suspend fun getAllOutOfStockQrCodes(): List<QrReaderDto>

    suspend fun getAllQrCodes(): List<QrReaderDto>
}
package com.xera.sanadqrreader.data.local

import com.xera.sanadqrreader.data.repository.entities.QrReaderDto
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sanadQrReaderDao: SanadQrReaderDao
) : LocalDataSource {

    override suspend fun saveScannedQrCode(qrCode: String) {
        val qrReaderDto = QrReaderDto(qrCode = qrCode, status = "in stock")
        sanadQrReaderDao.insertQrCode(qrReaderDto)
    }

    override suspend fun updateQrCodeState(qrCode: String, status: String) {
        sanadQrReaderDao.updateQrCodeStatus(qrCode, "out of stock")
    }

    override suspend fun isQrCodeExists(qrCode: String): Boolean {
        return sanadQrReaderDao.isQrCodeExists(qrCode)
    }

    override suspend fun getAllInStockQrCodes(): List<QrReaderDto> {
       return sanadQrReaderDao.getAllInStockQrCodes()
    }

    override suspend fun getAllOutOfStockQrCodes(): List<QrReaderDto> {
       return sanadQrReaderDao.getAllOutOfStockQrCodes()
    }

    override suspend fun getAllQrCodes(): List<QrReaderDto> {
       return sanadQrReaderDao.getAllQrCodes()
    }
}

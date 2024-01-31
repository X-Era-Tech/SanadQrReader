package com.xera.sanadqrreader.data.local

import com.xera.sanadqrreader.data.repository.entities.InStockProducts
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.data.repository.entities.ProductHistory
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sanadQrReaderDao: SanadQrReaderDao
) : LocalDataSource {

    override suspend fun saveInStockProduct(qrCode: String, getInTime: String,to: String,from: String,status: String,getOutTime: String) {
        sanadQrReaderDao.insertInStockProducts(
            InStockProducts(
                qrCode = qrCode,
                getInTime = getInTime,
                to = to,
                from = from,
                status = status,
                getOutTime = getOutTime
            )
        )
    }

    override suspend fun updateProductStatus(qrCode: String, getOutTime: String) {
        sanadQrReaderDao.updateProductGetOutDate(getOutDate = getOutTime, qrCode = qrCode)
    }

    override suspend fun isProductInStock(qrCode: String): Boolean {
        return sanadQrReaderDao.isProductInStock(qrCode)
    }

    override suspend fun getAllInStockProducts(): List<InStockProducts> {
        return sanadQrReaderDao.getAllInStockProducts()
    }

    override suspend fun saveOutStockProduct(qrCode: String, getOutTime: String,to: String, from: String,status: String,getInTime: String) {
        sanadQrReaderDao.insertOutStockProducts(
            OutStockProducts(
                qrCode = qrCode,
                getOutTime = getOutTime,
                to = to,
                from = from,
                status = status,
                getInTime = getInTime
            )
        )
    }

    override suspend fun isProductOutStock(qrCode: String): Boolean {
        return sanadQrReaderDao.isProductOutStock(qrCode)
    }

    override suspend fun getAllOutStockProducts(): List<OutStockProducts> {
        return sanadQrReaderDao.getAllOutStockProducts()
    }

    override suspend fun deleteInStockProduct(qrCode: String) {
        sanadQrReaderDao.deleteInStockProduct(qrCode)
    }

    override suspend fun deleteOutStockProduct(qrCode: String) {
        sanadQrReaderDao.deleteOutStockProduct(qrCode)
    }

    override suspend fun getInTime(qrCode: String): String {
        return sanadQrReaderDao.getInTimeForProduct(qrCode)
    }

    override suspend fun getOutTime(qrCode: String): String {
        return sanadQrReaderDao.getOutTimeForProduct(qrCode)
    }

    override suspend fun saveProductHistory(
        qrCode: String,
        getInTime: String?,
        getOutTime: String?,
        to: String,
        from: String,
        status: String
    ) {
        sanadQrReaderDao.insertProductHistory(
            ProductHistory(
                qrCode = qrCode,
                getInTime = getInTime,
                getOutTime = getOutTime,
                to = to,
                from = from,
                status = status
            )
        )
    }

    override suspend fun getProductHistory(qrCode: String): List<ProductHistory> {
        return sanadQrReaderDao.getProductHistory(qrCode)
    }
}

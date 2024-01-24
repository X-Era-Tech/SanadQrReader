package com.xera.sanadqrreader.domain.repository

import com.xera.sanadqrreader.data.repository.entities.InStockProducts
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.data.repository.entities.ProductHistory
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {

    fun startScanning(
        saveFunction: suspend (String?, String?, String, String) -> Unit,
        to: String,
        from: String
    ): Flow<String?>

    suspend fun saveInStockProduct(
        qrCode: String,
        getInTime: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String
    )

    suspend fun updateProductStatus(qrCode: String, getOutTime: String)

    suspend fun getAllInStockProduct(): List<InStockProducts>

    suspend fun getAllOutOfStockProducts(): List<OutStockProducts>

    suspend fun saveOutStockProduct(
        qrCode: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String,
        getInTime: String
    )

    suspend fun deleteInStockProduct(qrCode: String)

    suspend fun isProductInStock(qrCode: String): Boolean

    suspend fun isProductOutStock(qrCode: String): Boolean

    suspend fun deleteOutStockProduct(qrCode: String)

    suspend fun getInTimeForProduct(qrCode: String): String

    suspend fun getOutTimeForProduct(qrCode: String): String

    suspend fun getProductHistory(qrCode: String): List<ProductHistory>

    suspend fun insertProductHistory(qrCode: String, getInTime: String, getOutTime: String, to: String, from: String, status: String)
}
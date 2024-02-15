package com.xera.sanadqrreader.domain.repository

import com.xera.sanadqrreader.domain.models.AuthEntity
import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.models.OutStockProductEntity
import com.xera.sanadqrreader.domain.models.ProductHistoryEntity
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

    suspend fun updateProductStatusLocal(qrCode: String, getOutTime: String)

    suspend fun getAllInStockProductLocal(): List<InStockEntity>

    suspend fun getAllOutOfStockProductsLocal(): List<OutStockProductEntity>

    suspend fun saveOutStockProductLocal(
        qrCode: String,
        getOutTime: String,
        to: String,
        from: String,
        status: String,
        getInTime: String
    )

    suspend fun deleteInStockProductLocal(qrCode: String)

    suspend fun isProductInStockLocal(qrCode: String): Boolean

    suspend fun isProductOutStockLocal(qrCode: String): Boolean

    suspend fun deleteOutStockProductLocal(qrCode: String)

    suspend fun getInTimeForProductLocal(qrCode: String): String

    suspend fun getOutTimeForProductLocal(qrCode: String): String

    suspend fun getProductHistoryLocal(qrCode: String): List<ProductHistoryEntity>

    suspend fun insertProductHistoryLocal(qrCode: String, getInTime: String, getOutTime: String, to: String, from: String, status: String)

    suspend fun login(email: String, password: String): AuthEntity

    suspend fun register(email: String,name:String, password: String): AuthEntity

    suspend fun addProductInStockRemote(inStockEntity: InStockEntity)

    suspend fun addProductOutStockRemote(outStockProductEntity: OutStockProductEntity)

    suspend fun addProductHistoryRemote(productHistoryEntity: ProductHistoryEntity)

    suspend fun getAllInStockProductRemote(): List<InStockEntity>

    suspend fun getAllOutOfStockProductsRemote(): List<OutStockProductEntity>

    suspend fun getAllProductHistoryRemote(): List<ProductHistoryEntity>

    suspend fun updateInStockProductRemote(inStockEntity: InStockEntity)

    suspend fun updateOutStockProductRemote(outStockProductEntity: OutStockProductEntity)

    suspend fun updateProductHistoryRemote(productHistoryEntity: ProductHistoryEntity)

    suspend fun deleteInStockProductRemote(qrCode: String)

    suspend fun deleteOutStockProductRemote(qrCode: String)

    suspend fun deleteProductHistoryRemote(id: Int)

    suspend fun saveUserToken(token: String)

    suspend fun getUserToken(): String?


}
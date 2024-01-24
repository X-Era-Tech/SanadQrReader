package com.xera.sanadqrreader.data.local

import com.xera.sanadqrreader.data.repository.entities.InStockProducts
import com.xera.sanadqrreader.data.repository.entities.OutStockProducts
import com.xera.sanadqrreader.data.repository.entities.ProductHistory

interface LocalDataSource {

    suspend fun saveInStockProduct(qrCode: String,getInTime:String, to: String, from: String,status: String,getOutTime: String)

    suspend fun updateProductStatus(qrCode: String, getOutTime: String)

    suspend fun isProductInStock(qrCode: String): Boolean

    suspend fun getAllInStockProducts(): List<InStockProducts>

    suspend fun saveOutStockProduct(qrCode: String,getOutTime: String, to: String, from: String,status: String,getInTime: String)

    suspend fun isProductOutStock(qrCode: String): Boolean

    suspend fun getAllOutStockProducts(): List<OutStockProducts>

    suspend fun deleteInStockProduct(qrCode: String)

    suspend fun deleteOutStockProduct(qrCode: String)

    suspend fun getInTime(qrCode: String): String

    suspend fun getOutTime(qrCode: String): String


    suspend fun saveProductHistory(qrCode: String, getInTime: String?, getOutTime: String?, to: String, from: String, status: String)

    suspend fun getProductHistory(qrCode: String): List<ProductHistory>

}
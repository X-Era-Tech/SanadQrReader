package com.xera.sanadqrreader.data.repository.data_sources

import com.xera.sanadqrreader.data.repository.entities.InStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.OutStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.ProductHistoryDto

interface LocalDataSource {

    suspend fun saveInStockProduct(qrCode: String,getInTime:String, to: String, from: String,status: String,getOutTime: String)

    suspend fun updateProductStatus(qrCode: String, getOutTime: String)

    suspend fun isProductInStock(qrCode: String): Boolean

    suspend fun getAllInStockProducts(): List<InStockProductsDto>

    suspend fun saveOutStockProduct(qrCode: String,getOutTime: String, to: String, from: String,status: String,getInTime: String)

    suspend fun isProductOutStock(qrCode: String): Boolean

    suspend fun getAllOutStockProducts(): List<OutStockProductsDto>

    suspend fun deleteInStockProduct(qrCode: String)

    suspend fun deleteOutStockProduct(qrCode: String)

    suspend fun getInTime(qrCode: String): String

    suspend fun getOutTime(qrCode: String): String


    suspend fun saveProductHistory(qrCode: String, getInTime: String?, getOutTime: String?, to: String, from: String, status: String)

    suspend fun getProductHistory(qrCode: String): List<ProductHistoryDto>

}
package com.xera.sanadqrreader.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xera.sanadqrreader.data.repository.entities.InStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.OutStockProductsDto
import com.xera.sanadqrreader.data.repository.entities.ProductHistoryDto

@Dao
interface SanadQrReaderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInStockProducts(inStockProducts: InStockProductsDto)

    @Query("UPDATE in_stock_products SET getOutTime = :getOutDate WHERE qrCode = :qrCode")
    suspend fun updateProductGetOutDate(qrCode: String, getOutDate: String)

    @Query("SELECT EXISTS(SELECT 1 FROM in_stock_products WHERE qrCode = :qrCode)")
    suspend fun isProductInStock(qrCode: String): Boolean

    @Query("SELECT * FROM in_stock_products")
    suspend fun getAllInStockProducts(): List<InStockProductsDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOutStockProducts(outStockProducts: OutStockProductsDto)

    @Query("SELECT * FROM Out_Stock_Table")
    suspend fun getAllOutStockProducts(): List<OutStockProductsDto>

    @Query("SELECT EXISTS(SELECT 1 FROM out_stock_table WHERE qrCode = :qrCode)")
    suspend fun isProductOutStock(qrCode: String): Boolean

    @Query("DELETE FROM in_stock_products WHERE qrCode = :qrCode")
    suspend fun deleteInStockProduct(qrCode: String)

    @Query("DELETE FROM out_stock_table WHERE qrCode = :qrCode")
    suspend fun deleteOutStockProduct(qrCode: String)

    @Query("SELECT getInTime FROM in_stock_products WHERE qrCode = :qrCode")
    suspend fun getInTimeForProduct(qrCode: String): String

    @Query("SELECT getOutTime FROM out_stock_table WHERE qrCode = :qrCode")
    suspend fun getOutTimeForProduct(qrCode: String): String

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductHistory(productHistory: ProductHistoryDto)

    @Query("SELECT * FROM product_history WHERE qrCode = :qrCode ORDER BY id DESC")
    suspend fun getProductHistory(qrCode: String): List<ProductHistoryDto>


}
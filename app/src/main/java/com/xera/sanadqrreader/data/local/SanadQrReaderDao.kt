package com.xera.sanadqrreader.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xera.sanadqrreader.data.repository.entities.QrReaderDto

@Dao
interface SanadQrReaderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQrCode(qrReaderDto: QrReaderDto)

    @Query("UPDATE Qr_Reader_Table SET status = :status WHERE qrCode = :qrCode")
    suspend fun updateQrCodeStatus(qrCode: String, status: String)

    @Query("SELECT EXISTS(SELECT 1 FROM Qr_Reader_Table WHERE qrCode = :qrCode)")
    suspend fun isQrCodeExists(qrCode: String): Boolean

    @Query("SELECT * FROM Qr_Reader_Table WHERE status = 'in stock'")
    suspend fun getAllInStockQrCodes(): List<QrReaderDto>

    @Query("SELECT * FROM Qr_Reader_Table WHERE status = 'out of stock'")
    suspend fun getAllOutOfStockQrCodes(): List<QrReaderDto>

    @Query("SELECT * FROM Qr_Reader_Table")
    suspend fun getAllQrCodes(): List<QrReaderDto>

}
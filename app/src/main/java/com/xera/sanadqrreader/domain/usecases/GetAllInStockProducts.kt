package com.xera.sanadqrreader.domain.usecases

import android.content.Context
import com.xera.sanadqrreader.data.excel.In_Stock
import com.xera.sanadqrreader.data.excel.createExcel
import com.xera.sanadqrreader.data.excel.createWorkbook
import com.xera.sanadqrreader.domain.models.InStockEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import org.apache.poi.ss.util.CellUtil.createCell
import javax.inject.Inject

class GetAllInStockProducts @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke():List<InStockEntity>{
        return scannerRepository.getAllInStockProductRemote()
    }

     fun writeProductsToExcel(context: Context, products: List<InStockEntity>) {
        val workbook = createWorkbook(1)

        val sheet = workbook.getSheet(In_Stock)

        for ((index, product) in products.withIndex()) {
            val row = sheet.createRow(index + 1)
            createCell(row, 0, product.qrCode)
            createCell(row, 1, product.getInTime)
            createCell(row, 2, product.getOutTime)
            createCell(row, 3, product.to)
            createCell(row, 4, product.from)
            createCell(row, 5, product.status)
        }

        createExcel(context, workbook,"In_Stock.xlsx")
    }

}

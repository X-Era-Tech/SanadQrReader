package com.xera.sanadqrreader.domain.usecases

import android.content.Context
import com.xera.sanadqrreader.data.excel.Out_Stock
import com.xera.sanadqrreader.data.excel.createExcel
import com.xera.sanadqrreader.data.excel.createWorkbook
import com.xera.sanadqrreader.domain.models.OutStockProductEntity
import com.xera.sanadqrreader.domain.repository.ScannerRepository
import org.apache.poi.ss.util.CellUtil
import javax.inject.Inject

class GetAllOutOfStockProducts @Inject constructor(
    private val scannerRepository: ScannerRepository
){
    suspend operator fun invoke() = scannerRepository.getAllOutOfStockProductsRemote()

     fun writeProductsToExcel(context: Context, products: List<OutStockProductEntity>) {
        val workbook = createWorkbook(1)

        val sheet = workbook.getSheet(Out_Stock)

        for ((index, product) in products.withIndex()) {
            val row = sheet.createRow(index + 1)
            CellUtil.createCell(row, 0, product.qrCode)
            CellUtil.createCell(row, 1, product.getInTime)
            CellUtil.createCell(row, 2, product.getOutTime)
            CellUtil.createCell(row, 3, product.to)
            CellUtil.createCell(row, 4, product.from)
            CellUtil.createCell(row, 5, product.status)
        }

        createExcel(context, workbook ,"Out_Stock.xlsx")
    }}
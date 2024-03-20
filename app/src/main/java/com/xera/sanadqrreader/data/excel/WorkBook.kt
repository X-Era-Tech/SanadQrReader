package com.xera.sanadqrreader.data.excel

import android.content.Context
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

const val In_Stock = "In_Stock"
const val Out_Stock = "Out_Stock"
fun createWorkbook(rowIndex: Int): Workbook {
    // Creating excel workbook
    val workbook = XSSFWorkbook()
    //Creating first sheet inside workbook
    //Constants.SHEET_NAME is a string value of sheet name
    val sheet: Sheet = workbook.createSheet(In_Stock)
    val sheet2: Sheet = workbook.createSheet(Out_Stock)
    //Create Header Cell Style
    val cellStyle = getHeaderStyle(workbook)
    //Creating sheet header row
    createSheetHeader(cellStyle, sheet)
    createSheetHeader(cellStyle, sheet2)
    //Adding data to the sheet
    addData(rowIndex, sheet)
    return workbook
}

private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
    //setHeaderStyle is a custom function written below to add header style

    //Create sheet first row
    val row = sheet.createRow(0)

    //Header list
    val HEADER_LIST = listOf("QrCode", "getInTime", "getOutTime","to", "from", "status")

    //Loop to populate each column of header row
    for ((index, value) in HEADER_LIST.withIndex()) {

        val columnWidth = (15 * 500)

        //index represents the column number
        sheet.setColumnWidth(index, columnWidth)
        

        //Create cell
        val cell = row.createCell(index)

        //value represents the header value from HEADER_LIST
        cell?.setCellValue(value)

        //Apply style to cell
        cell.cellStyle = cellStyle
    }
}

private fun getHeaderStyle(workbook: Workbook): CellStyle {

    //Cell style for header row
    val cellStyle: CellStyle = workbook.createCellStyle()

    //Apply cell color
    val colorMap: IndexedColorMap = (workbook as XSSFWorkbook).stylesSource.indexedColors
    var color = XSSFColor(IndexedColors.RED, colorMap).indexed
    cellStyle.fillForegroundColor = color
    cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

    //Apply font style on cell text
    val whiteFont = workbook.createFont()
    color = XSSFColor(IndexedColors.WHITE, colorMap).indexed
    whiteFont.color = color
    whiteFont.bold = true
    cellStyle.setFont(whiteFont)


    return cellStyle
}

private fun createCell(row: Row, columnIndex: Int, value: String?) {
    val cell = row.createCell(columnIndex)
    cell?.setCellValue(value)
}

private fun addData(rowIndex: Int, sheet: Sheet) {

    //Create row based on row index
    val row = sheet.createRow(rowIndex)

    //Add data to each cell
    createCell(row, 0, "hassa") //Column 1
    createCell(row, 1, "sad 2") //Column 2
    createCell(row, 2, "sadsad 3") //Column 3
}

fun createExcel(context: Context, workbook: Workbook,fileName:String) {

    val appDirectory = context.getExternalFilesDir("SanadQrReader")

    if (appDirectory != null && !appDirectory.exists()) {
        appDirectory.mkdirs()
    }

    val excelFile = File(appDirectory, fileName)

    try {
        val fileOut = FileOutputStream(excelFile)
        workbook.write(fileOut)
        fileOut.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
package me.elyor.spreadsheet.writer.excel;

import me.elyor.spreadsheet.writer.SpreadsheetRenderResource;
import me.elyor.spreadsheet.writer.SpreadsheetRenderResourceFactory;
import me.elyor.spreadsheet.writer.SpreadsheetWriter;
import me.elyor.spreadsheet.writer.exception.SpreadsheetException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;


/**
 * Excel spreadsheet writer that uses Apache POI SXSSFWorkbook.
 * This implementation is memory efficient since streaming is used when writing.
 * Therefore, this implementation is a very good candidate to work with huge Excel files.
 *<br>
 * The supported version is Excel 2007(XLSX)
 *
 * @author Elyorbek Ibrokhimov
 * */
public class ExcelWriter<T> implements SpreadsheetWriter {

    private static final SpreadsheetVersion spreadsheetVersion =
            SpreadsheetVersion.EXCEL2007;
    private static final int ROW_START_INDEX = 1;
    private static final int COLUMN_START_INDEX = 0;
    private int currentRowIndex = ROW_START_INDEX;

    private final SXSSFWorkbook workbook;
    private final Sheet sheet;
    private SpreadsheetRenderResource renderResource;

    public ExcelWriter(List<T> rows, Class<T> type) {
        validate(rows);
        renderResource = SpreadsheetRenderResourceFactory
                .createRenderResource(type);
        this.workbook = new SXSSFWorkbook();
        this.sheet = workbook.createSheet(renderResource.sheetName());
        renderExcel(rows);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        workbook.close();
        workbook.dispose();
        outputStream.close();
    }

    private void renderExcel(List<T> rows) {
        applyColumnWidths(renderResource.columnWidths());
        renderHeaders(currentRowIndex++);
        if(rows.isEmpty())
            return;
        for(Object rowData : rows) {
            renderBody(rowData, currentRowIndex++);
        }
    }

    private void validate(List<T> rows) {
        if(rows == null) {
            throw new IllegalArgumentException("Rows must not be null");
        }

        int maxRows = spreadsheetVersion.getMaxRows();
        if(rows.size() > maxRows) {
            String message = String
                    .format("%d exceeds maximum supported %d rows",
                    rows.size(), maxRows);
            throw new IllegalArgumentException(message);
        }
    }

    private void applyColumnWidths(int[] columnWidths) {
        for(int i = 0; i < columnWidths.length; i++) {
            int width = columnWidths[i];
            sheet.setColumnWidth(i, width);
        }
    }

    private void renderHeaders(int rowIndex) {
        ExcelStyler styler = new ExcelStyler();
        CellStyle headerStyle = styler.createDefaultHeaderStyle(workbook);
        Row row = sheet.createRow(rowIndex);
        int columnIndex = COLUMN_START_INDEX;
        for(String headerName : renderResource.headerNames()) {
           Cell cell = row.createCell(columnIndex++);
           cell.setCellStyle(headerStyle);
           cell.setCellValue(headerName);
        }
    }

    private void renderBody(Object rowData, int rowIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = COLUMN_START_INDEX;
        for (String fieldName : renderResource.fieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = FieldUtils.getField(rowData.getClass(), fieldName, true);
                Object cellValue = field.get(rowData);
                renderCellValue(cell, cellValue);
            } catch (Exception e) {
                throw new SpreadsheetException(e.getMessage());
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number numberValue) {
            cell.setCellValue(numberValue.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }
}

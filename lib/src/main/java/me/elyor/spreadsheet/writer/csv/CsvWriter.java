package me.elyor.spreadsheet.writer.csv;

import me.elyor.spreadsheet.writer.SpreadsheetRenderResource;
import me.elyor.spreadsheet.writer.SpreadsheetRenderResourceFactory;
import me.elyor.spreadsheet.writer.SpreadsheetWriter;
import me.elyor.spreadsheet.writer.exception.SpreadsheetException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;

/**
 * CSV spreadsheet writer that uses Apache Commons CSV under the hood.
 *
 * @author Elyorbek Ibrokhimov
 * */
public class CsvWriter<T> implements SpreadsheetWriter {

    private SpreadsheetRenderResource renderResource;
    private List<T> rows;

    public CsvWriter(List<T> rows, Class<T> type) {
        this.rows = rows;
        renderResource = SpreadsheetRenderResourceFactory
                .createRenderResource(type);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        BufferedWriter writer = writerFromOutputStream(outputStream);
        writeRecords(writer);
        outputStream.close();
    }

    private void writeRecords(BufferedWriter writer) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(renderResource.headerNames()).build();
        CSVPrinter csvPrinter = new CSVPrinter(writer, format);
        for(Object row : rows) {
            for (String fieldName : renderResource.fieldNames()) {
                try {
                    Field field = FieldUtils.getField(row.getClass(), fieldName, true);
                    Object value = field.get(row);
                    csvPrinter.print(value);
                } catch (Exception e) {
                    throw new SpreadsheetException(e.getMessage());
                }
            }
            csvPrinter.println();
        }
    }

    private BufferedWriter writerFromOutputStream(OutputStream stream) {
        return new BufferedWriter(new OutputStreamWriter(stream));
    }
}

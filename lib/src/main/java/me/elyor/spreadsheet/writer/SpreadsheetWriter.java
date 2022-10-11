package me.elyor.spreadsheet.writer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for writing spreadsheets
 *
 * @implSpec All the implementing classes has to make use of {@link Spreadsheet}
 *
 * @author Elyorbek Ibrokhimov
 */
public interface SpreadsheetWriter {

    /**
     * Writes the spreadsheet to output stream and releases any system resources
     * associated with this stream. The general contract of {@code write }
     * is that it closes the output stream after finishing writing.
     * After {@code write} is called the output stream cannot be opened and
     * output operations can not be performed.
     * @param outputStream outputStream to be written to
     * @throws IOException  if an I/O error occurs.
     * */
    void write(OutputStream outputStream) throws IOException;
}

package me.elyor.spreadsheet.writer.csv;

import me.elyor.spreadsheet.writer.SpreadsheetDto;
import me.elyor.spreadsheet.writer.SpreadsheetWriter;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CsvWriterTests {

    @Test
    void whenWriteToFileThenNoError() throws IOException {
        Path csvFile = Files.createTempFile("csv-test", ".csv");
        try (FileOutputStream fos = new FileOutputStream(csvFile.toFile())) {
            SpreadsheetDto one = new SpreadsheetDto(1, 50, "First");
            SpreadsheetDto two = new SpreadsheetDto(2, 10, "Second");
            List<SpreadsheetDto> rows = Arrays.asList(one, two);
            SpreadsheetWriter writer = new CsvWriter<>(rows, SpreadsheetDto.class);
            assertDoesNotThrow(() -> writer.write(fos));
        }
    }
}

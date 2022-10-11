package me.elyor.spreadsheet.writer.excel;

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

class ExcelWriterTests {

    @Test
    void whenWriteToFileThenNoError() throws IOException {
        Path excelFile = Files.createTempFile("excel-test", ".xlsx");
        try (FileOutputStream fos = new FileOutputStream(excelFile.toFile())) {
            SpreadsheetDto one = new SpreadsheetDto(1, 50, "First");
            SpreadsheetDto two = new SpreadsheetDto(2, 10, "Second");
            List<SpreadsheetDto> rows = Arrays.asList(one, two);
            SpreadsheetWriter writer = new ExcelWriter<>(rows, SpreadsheetDto.class);
            assertDoesNotThrow(() -> writer.write(fos));
        }
    }
}

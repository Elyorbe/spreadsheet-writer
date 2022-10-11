package me.elyor.spreadsheet.writer;

import me.elyor.spreadsheet.writer.exception.NoSpreadsheetAnnotationException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpreadsheetRenderResourceFactoryTests {

    @Test
    void whenCreateRenderResourceFromNotAnnotatedTypThenError() {
        Class<NoSpreadsheetAnnotationException> expectedType =
                NoSpreadsheetAnnotationException.class;
        Class<SpreadsheetDtoWithoutAnnotation> givenType =
                SpreadsheetDtoWithoutAnnotation.class;
        assertThrows(expectedType,
                () -> SpreadsheetRenderResourceFactory
                        .createRenderResource(givenType));
    }

    @Test
    void whenCreateRenderResourceThenExpectCorrectResource() {
        List<String> expectedFieldNames = Arrays.asList("id", "quantity", "category");
        String[] expectedHeaderNames = new String[]{ "ID", "Quantity", "Category" };
        int[] expectedColumnWidths = { 10 * 256, 25 * 256, 50 * 256 };
        String expectedSheetName = "Spreadsheet";
        SpreadsheetRenderResource resource = SpreadsheetRenderResourceFactory
                .createRenderResource(SpreadsheetDto.class);
        assertIterableEquals(expectedFieldNames, resource.fieldNames());
        assertArrayEquals(expectedHeaderNames, resource.headerNames());
        assertArrayEquals(expectedColumnWidths, resource.columnWidths());
        assertEquals(expectedSheetName, resource.sheetName());
    }

    static class SpreadsheetDtoWithoutAnnotation {
        private int id;
        private int description;
    }
}

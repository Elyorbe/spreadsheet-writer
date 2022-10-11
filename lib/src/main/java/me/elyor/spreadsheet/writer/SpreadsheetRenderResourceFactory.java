package me.elyor.spreadsheet.writer;

import me.elyor.spreadsheet.writer.exception.NoSpreadsheetAnnotationException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SpreadsheetRenderResourceFactory {

    public static SpreadsheetRenderResource createRenderResource(Class<?> clazz) {
        validate(clazz);
        SpreadsheetRenderResource.Builder builder = SpreadsheetRenderResource.builder();
        Spreadsheet spreadsheet = clazz.getAnnotation(Spreadsheet.class);
        List<String> fieldNames = new ArrayList<>();
        for(Field field : FieldUtils.getAllFieldsList(clazz)) {
            fieldNames.add(field.getName());
        }
        return builder.columnWidths(spreadsheet.columnWidths())
                .headerNames(spreadsheet.headerNames())
                .sheetName(spreadsheet.sheetName())
                .fieldNames(fieldNames).build();
    }

    private static void validate(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(Spreadsheet.class)) {
            String message = String
                    .format("Data class must be annotated with %s",
                            Spreadsheet.class.getName());
            throw new NoSpreadsheetAnnotationException(message);
        }
    }
}

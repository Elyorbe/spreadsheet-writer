package me.elyor.spreadsheet.writer;

import java.lang.annotation.*;

/**
 * Use this annotation on data class when using {@link SpreadsheetWriter}
 * It gets all the fields of the data class(and it's super class if any) through reflection.
 * Fields of the data class doesn't have to public.
 *
 * <p>
 * Usage example:
 * </p>
 *
 * <pre class="code">
 * &#064;Spreadsheet(sheetName = "Product",
 *         headerNames = { "ID", "QUANTITY", "Category" },
 *         columnWidths = { 10 * 256, 25 * 256, 50 * 256 }
 * )
 * public class SpreadsheetDto {
 *     private int id;
 *     private int quantity;
 *     private String category;
 *
 *     public SpreadsheetDto(int id, int quantity, String category) {
 *         this.id = id;
 *         this.quantity = quantity;
 *         this.category = category;
 *     }
 * }
 * </pre>
 *
 * @author Elyorbek Ibrokhimov
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Spreadsheet {

    /**
     * Name of the sheet to be used if multi tab/sheet if supported
     * by implementing format
     *
     * @return Name of the sheet
     */
    String sheetName() default "Sheet";

    /**
     * Header names of the spreadsheet. The order must align with data
     * class fields.
     *
     * @return header names
     */
    String[] headerNames() default{};

    /**
     * Column widths to be applied if supported by implementing format.
     * The order must align with data header names.
     * <br>
     * For Excel file formats column width factor is 256. Therefore,
     * if the width is defined in terms of number of characters
     * desired number of characters should be multiplied by 256
     * <p>
     *     Example usage:
     * </p>
     * {@code columnWidths = { 10 * 256, 25 * 256, 50 * 256 }}
     * @return header names
     */
    int[] columnWidths() default {};
}

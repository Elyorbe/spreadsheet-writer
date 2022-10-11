package me.elyor.spreadsheet.writer;

@Spreadsheet(sheetName = "Spreadsheet",
        headerNames = { "ID", "Quantity", "Category" },
        columnWidths = { 10 * 256, 25 * 256, 50 * 256 }
)
public class SpreadsheetDto {
    private int id;
    private int quantity;
    private String category;

    public SpreadsheetDto(int id, int quantity, String category) {
        this.id = id;
        this.quantity = quantity;
        this.category = category;
    }
}

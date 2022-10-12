# Spreadsheet Writer

Simple yet effective, memory efficient and easy to use spreadsheet writer library.
The aim of the project is to enable the rapid development of spreadsheet exporting features
for not complex spreadsheets. It provides basic functionalities and can be used for large datasets.

## Adding to your build
Maven:

```xml
<dependency>
    <groupId>me.elyor</groupId>
    <artifactId>spreadsheet-writer</artifactId>
    <version>0.1.0</version>
</dependency>
```

Gradle:
```groovy
dependencies {
    implementation 'me.elyor:spreadsheet-writer:0.1.0'
}
```

## Usage guide
Data class must be annotated with `@Spreadsheet` annotation before writing to output stream. `@Spreadsheet` annotation
accepts `sheetName`, `headerNames` and `columnWidths` as parameters. All of them are optional. The order of headerNames
and columnWidths must align with the order of data class fields. If underlying implementation doesn't support 
any of these they are simply ignored. The fields of `@Spreadsheet` annotated class doesn't have to public.

## Examples

Data holding class:
```java
@Spreadsheet(sheetName = "Products",
        headerNames = { "ID", "Quantity", "Category" },
        columnWidths = { 10 * 256, 25 * 256, 50 * 256 }
)
public class Product {
    private int id;
    private int quantity;
    private String category;

    public Product(int id, int quantity, String category) {
        this.id = id;
        this.quantity = quantity;
        this.category = category;
    }
}
```
Note: For Excel file formats column width factor is 256. Therefore, if the width is defined in terms of number 
of characters desired number of characters should be multiplied by 256. As in the example: 
`columnWidths = { 10 * 256, 25 * 256, 50 * 256 }`

### Writing to Excel file:
```java
class ExcelWriterTest {
    @Test
    void whenWriteToFileThenNoError() throws IOException {
        Path excelFile = Files.createTempFile("products", ".xlsx");
        try (FileOutputStream fos = new FileOutputStream(excelFile.toFile())) {
            Product book = new Product(1, 50, "Book");
            Product pencil = new Product(2, 10, "Pencil");
            List<Product> rows = Arrays.asList(one, two);
            SpreadsheetWriter writer = new ExcelWriter<>(rows, Product.class);
            assertDoesNotThrow(() -> writer.write(fos));
        }
    }
}
```

### Exporting to Excel file from Spring Boot

```java
@RestController
@RequstMapping("/products")
public class ProductController {

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) {
        List<Product> rows = productService.getExcelData();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String downloadFileName = String.format("product-%s.xlsx", now);
        response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);

        SpreadsheetWriter writer = new ExcelWriter<>(rows, Product.class);
        writer.write(response.getOutputStream());
    }
}
```

### Writing to CSV file:
Writing to CSV file is almost similar to Excel writing because of interface based approach:
```java
class CsvWriterTest {
    @Test
    void whenWriteToFileThenNoError() throws IOException {
        Path excelFile = Files.createTempFile("products", ".csv");
        try (FileOutputStream fos = new FileOutputStream(excelFile.toFile())) {
            Product book = new Product(1, 50, "Book");
            Product pencil = new Product(2, 10, "Pencil");
            List<Product> rows = Arrays.asList(one, two);
            SpreadsheetWriter writer = new CsvWriter<>(rows, Product.class);
            assertDoesNotThrow(() -> writer.write(fos));
        }
    }
}
```

### Exporting to CSV file from Spring Boot
Exporting to CSV file is also almost the same but just with minor changes:
```java
@RestController
@RequstMapping("/products")
public class ProductController {

    @GetMapping("/csv")
    public void exportToExcel(HttpServletResponse response) {
        List<Product> rows = productService.getExcelData();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String downloadFileName = String.format("product-%s.csv", now);
        response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);

        SpreadsheetWriter writer = new CsvWriter<>(rows, Product.class);
        writer.write(response.getOutputStream());
    }
}
```
## License

You are free to use this code under the
[MIT License](https://github.com/Elyorbe/spreadsheet-writer/blob/main/LICENSE.md).

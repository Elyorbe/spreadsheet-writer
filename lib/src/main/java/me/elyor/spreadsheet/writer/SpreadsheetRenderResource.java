package me.elyor.spreadsheet.writer;

import java.util.List;

public class SpreadsheetRenderResource {

    private List<String> fieldNames;
    private String[] headerNames;
    private int[] columnWidths;
    private String sheetName;

    private SpreadsheetRenderResource(Builder builder) {
        this.fieldNames = builder.fieldNames;
        this.headerNames = builder.headerNames;
        this.columnWidths = builder.columnWidths;
        this.sheetName = builder.sheetName;
    }

    public String sheetName() {
        return sheetName;
    }

    public String[] headerNames() {
        return headerNames;
    }

    public int[] columnWidths() {
        return columnWidths;
    }

    public List<String> fieldNames() {
        return fieldNames;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private List<String> fieldNames;
        private String[] headerNames;
        private int[] columnWidths;
        private String sheetName;

        public Builder fieldNames(List<String> fieldNames) {
            this.fieldNames = fieldNames;
            return this;
        }

        public Builder headerNames(String[] headerNames) {
            this.headerNames = headerNames;
            return this;
        }

        public Builder columnWidths(int[] columnWidths) {
            this.columnWidths = columnWidths;
            return this;
        }

        public Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public SpreadsheetRenderResource build() {
            return new SpreadsheetRenderResource(this);
        }
    }

}

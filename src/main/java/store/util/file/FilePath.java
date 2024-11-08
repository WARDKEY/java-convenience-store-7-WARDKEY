package store.util.file;

public enum FilePath {
    PRODUCTS_FILE_PATH("src/main/resources/products.md"),
    PROMOTIONS_FILE_PATH("src/main/resources/promotions.md");

    private final String filePath;

    FilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}

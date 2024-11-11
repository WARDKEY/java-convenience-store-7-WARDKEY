package store.model;

import java.util.List;
import store.util.message.Error;

public class Products {
    private final List<Stock> products;

    public Products(List<Stock> products) {
        this.products = products;
    }

    public void validProduct(Products products, String productName, String productQuantity) {
        isBlankProductName(productName);
        isBlankProductQuantity(productQuantity);
        int quantity = Integer.parseInt(productQuantity);
        isZeroQuantity(quantity);
        isNotExistProduct(products, productName);
        isExceedQuantity(products, productName, quantity);
    }

    public List<Stock> findStocksByName(String productName) {
        return products.stream()
                .filter(stock -> stock.getName().equals(productName))
                .toList();
    }

    private void isBlankProductName(String productName) {
        if (productName.isBlank()) {
            throw new IllegalArgumentException(Error.ERROR_INVALID_FORMAT.getDescription());
        }
    }

    private void isBlankProductQuantity(String productQuantity) {
        if (productQuantity.isBlank()) {
            throw new IllegalArgumentException(Error.ERROR_INVALID_FORMAT.getDescription());
        }
    }

    private void isZeroQuantity(int quantity) {
        if (quantity == 0) {
            throw new IllegalArgumentException(Error.ERROR_INVALID_FORMAT.getDescription());
        }
    }

    private void isNotExistProduct(Products products, String productName) {
        boolean productExists = products.getProducts().stream()
                .anyMatch(stock -> stock.getName().equals(productName));

        if (!productExists) {
            throw new IllegalArgumentException(Error.ERROR_PRODUCT_NOT_EXIST.getDescription());
        }
    }

    private void isExceedQuantity(Products products, String productName, int quantity) {
        int totalQuantity = products.getProducts().stream()
                .filter(stock -> stock.getName().equals(productName))
                .mapToInt(Stock::getQuantity)
                .sum();

        if (quantity > totalQuantity) {
            throw new IllegalArgumentException(Error.ERROR_QUANTITY_EXCEEDS_STOCK.getDescription());
        }
    }

    public List<Stock> getProducts() {
        return products;
    }
}

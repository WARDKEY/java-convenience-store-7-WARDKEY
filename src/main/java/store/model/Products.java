package store.model;

import java.util.List;

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
            throw new IllegalArgumentException("[ERROR] 상품을 입력해주세요.");
        }
    }

    private void isBlankProductQuantity(String productQuantity) {
        if (productQuantity.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 수량을 입력해주세요.");
        }
    }

    private void isZeroQuantity(int quantity) {
        if (quantity == 0) {
            throw new IllegalArgumentException("[ERROR] 수량을 입력해주세요.");
        }
    }

    private void isNotExistProduct(Products products, String productName) {
        boolean productExists = products.getProducts().stream()
                .anyMatch(stock -> stock.getName().equals(productName));

        if (!productExists) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void isExceedQuantity(Products products, String productName, int quantity) {
        products.getProducts().stream()
                .filter(stock -> stock.getName().equals(productName))
                .findFirst()
                .ifPresent(stock -> {
                    if (quantity > Integer.parseInt(stock.getQuantity())) {
                        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
                    }
                });
    }

    public List<Stock> getProducts() {
        return products;
    }
}

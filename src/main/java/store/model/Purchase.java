package store.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Purchase {
    private final LinkedHashMap<String, String> purchases;
    private final LinkedHashMap<String, Integer> freeProducts;
    private final String purchase;

    public Purchase(String purchase, Products products) {
        this.purchases = new LinkedHashMap<>();
        this.freeProducts = new LinkedHashMap<>();
        validatePurchase(purchase, products);
        this.purchase = purchase;
    }

    private void validatePurchase(String purchase, Products products) {
        hasNotPurchase(purchase);

        doseNotEnterPurchase(purchase);

        List<String> purchaseProducts = Arrays.stream(purchase.split(",")).toList();

        checkPurchase(products, purchaseProducts);
    }

    private void hasNotPurchase(String purchase) {
        if (purchase == null || purchase.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품을 입력해주세요.");
        }
    }

    private void doseNotEnterPurchase(String purchase) {
        if (purchase.endsWith(",")) {
            throw new IllegalArgumentException("[ERROR] 상품을 이어서 입력해주세요.");
        }
    }

    private void checkPurchase(Products products, List<String> purchaseProducts) {
        for (String purchaseProduct : purchaseProducts) {
            doseNotStartAndEndDelimiter(purchaseProduct);
            inValidDelimiter(purchaseProduct);
            String productName = purchaseProduct.replaceAll("\\[([^\\-]+)-.*\\]", "$1").trim();
            String productQuantity = purchaseProduct.replaceAll(".*-(\\d+)\\]", "$1").trim();
            products.validProduct(products, productName, productQuantity);
            purchases.put(productName, productQuantity);
        }
    }

    private void doseNotStartAndEndDelimiter(String purchaseProduct) {
        if (!(purchaseProduct.startsWith("[") && purchaseProduct.endsWith("]"))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
        }
    }

    private void inValidDelimiter(String purchaseProduct) {
        if (!purchaseProduct.contains("-")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
        }

        if (purchaseProduct.indexOf("-") != purchaseProduct.lastIndexOf("-")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
        }

    }

    public void updateQuantity(String productName, int quantity) {
        if (purchases.containsKey(productName)) {
            purchases.put(productName, String.valueOf(quantity));
        }
    }

    public void addFreeProducts(String productName, int quantity) {
        freeProducts.put(productName, freeProducts.getOrDefault(productName, 0) + quantity);
    }

    public LinkedHashMap<String, String> getPurchases() {
        return purchases;
    }

    public LinkedHashMap<String, Integer> getFreeProducts() {
        return freeProducts;
    }

    public String getPurchase() {
        return purchase;
    }
}
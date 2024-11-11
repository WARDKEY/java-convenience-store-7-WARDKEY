package store.model;

import java.util.LinkedHashMap;

public class Receipt {
    private final LinkedHashMap<String, Integer> purchasedProducts;
    private final LinkedHashMap<String, Integer> freeProducts;
    private final LinkedHashMap<String, Integer> productPrices;
    private final boolean isMember;

    private int totalPurchaseAmount;
    private int totalDiscountAmount;
    private int sumNonPromotion;
    private int membershipDiscountAmount;
    private int finalAmount;

    public Receipt(Purchase purchase, Products products, Promotions promotions, boolean isMember) {
        this.purchasedProducts = new LinkedHashMap<>();
        this.productPrices = new LinkedHashMap<>();
        initPurchasedProductsAndPrice(purchase, products);
        this.freeProducts = purchase.getFreeProducts();
        this.isMember = isMember;
        calculateAmounts(promotions, products);
    }

    private void initPurchasedProductsAndPrice(Purchase purchase, Products products) {
        purchase.getPurchases().forEach((product, price) -> {
            purchasedProducts.put(product, Integer.parseInt(price));
            productPrices.put(product, findProductPrice(products, product));
        });
    }

    private void calculateAmounts(Promotions promotions, Products products) {
        calculateTotalPurchaseAmount();
        calculateTotalDiscountAmount();
        calculateSumNonPromotion(promotions, products);
        calculateMembershipDiscountAmount();
        calculateFinalAmount();
    }

    private void calculateTotalPurchaseAmount() {
        totalPurchaseAmount = purchasedProducts.entrySet().stream()
                .mapToInt(entry -> {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();
                    int price = productPrices.get(productName);
                    return price * quantity;
                })
                .sum();
    }

    private void calculateTotalDiscountAmount() {
        totalDiscountAmount = freeProducts.entrySet().stream()
                .mapToInt(entry -> {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();
                    int price = productPrices.get(productName);
                    return price * quantity;
                })
                .sum();
    }

    private void calculateSumNonPromotion(Promotions promotions, Products products) {
        sumNonPromotion = purchasedProducts.entrySet().stream()
                .filter(entry -> !promotions.isPromotionProduct(entry.getKey(), products))
                .mapToInt(entry -> {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();
                    int price = productPrices.get(productName);
                    return price * quantity;
                })
                .sum();
    }

    private void calculateMembershipDiscountAmount() {
        if (!isMember) {
            membershipDiscountAmount = 0;
            return;
        }
        int amountAfterEventDiscount = totalPurchaseAmount - totalDiscountAmount;
        membershipDiscountAmount = (int) (amountAfterEventDiscount * 0.3);
        if (membershipDiscountAmount > 8000) {
            membershipDiscountAmount = 8000;
        }
    }

    private void calculateFinalAmount() {
        finalAmount = totalPurchaseAmount - totalDiscountAmount - membershipDiscountAmount;
    }

    private int findProductPrice(Products products, String productName) {
        return products.getProducts().stream()
                .filter(stock -> stock.getName().equals(productName))
                .findFirst()
                .map(Stock::getPrice)
                .orElse(0);
    }

    public LinkedHashMap<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public LinkedHashMap<String, Integer> getFreeProducts() {
        return freeProducts;
    }

    public LinkedHashMap<String, Integer> getProductPrices() {
        return productPrices;
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public int getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public int getSumNonPromotion() {
        return sumNonPromotion;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }
}


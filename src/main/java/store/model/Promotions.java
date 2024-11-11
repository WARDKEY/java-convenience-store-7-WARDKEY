package store.model;

import java.util.List;

public class Promotions {

    private final List<Discount> promotions;

    public Promotions(List<Discount> promotions) {
        this.promotions = promotions;
    }

    public Discount findPromotionByStock(Stock stock) {
        return promotions.stream()
                .filter(discount -> discount.getName().equals(stock.getPromotion()))
                .findFirst()
                .orElse(null);
    }

    public void addPromotion(Stock stock, StringBuilder builder) {
        if (stock.getPromotion() != null) {
            builder.append(" ").append(stock.getPromotion());
        }
    }

    public boolean isPromotionProduct(String productName, Products products) {
        return products.findStocksByName(productName).stream()
                .anyMatch(stock -> stock.getPromotion() != null && promotions.stream()
                        .anyMatch(discount -> discount.getName().equals(stock.getPromotion())));
    }
}

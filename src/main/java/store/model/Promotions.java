package store.model;

import java.util.List;

public class Promotions {

    private final List<Discount> promotions;

    public Promotions(List<Discount> promotions) {
        this.promotions = promotions;
    }

    public void addPromotion(Stock stock, StringBuilder builder) {
        if (stock.getPromotion() != null) {
            builder.append(" ").append(stock.getPromotion());
        }
    }

    public List<Discount> getPromotions() {
        return promotions;
    }
}

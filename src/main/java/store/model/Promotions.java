package store.model;

public class Promotions {

    public void addPromotion(Stock stock, StringBuilder builder) {
        if (stock.getPromotion() != null) {
            builder.append(" ").append(stock.getPromotion());
        }
    }
}

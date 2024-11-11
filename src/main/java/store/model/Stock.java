package store.model;

import store.util.message.Error;

public class Stock {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Stock(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.quantity = Integer.parseInt(quantity);
        promotion = isPromotionNull(promotion);
        this.promotion = promotion;
    }

    private String isPromotionNull(String promotion) {
        if ("null".equals(promotion)) {
            promotion = null;
        }
        return promotion;
    }

    public void addNameAndPrice(Stock stock, StringBuilder builder) {
        try {
            int price = stock.getPrice();
            builder.append("- ").append(stock.getName()).append(" ")
                    .append(String.format("%,d원", price)).append(" ");
        } catch (NumberFormatException e) {
            System.out.println(Error.ERROR_INVALID_INPUT);
        }
    }

    public void addQuantity(Stock stock, StringBuilder builder) {
        String quantityStr = stock.getQuantitytoString();
        builder.append(quantityStr);
    }

    public void reduceStock(int requestedQuantity) {
        if (quantity < requestedQuantity) {
            throw new IllegalArgumentException(Error.ERROR_INVALID_INPUT.getDescription());
        }
        quantity -= requestedQuantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getQuantitytoString() {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }
}

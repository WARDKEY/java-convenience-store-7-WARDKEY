package store.model;

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
            System.out.println("가격 형식이 잘못되었습니다 : " + stock.getPrice());
        }
    }

    public void addQuantity(Stock stock, StringBuilder builder) {
        String quantityStr = stock.getQuantitytoString();
        builder.append(quantityStr);
    }

    public void reduceStock(int requestedQuantity) {
        if (quantity < requestedQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
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

package store.model;

public class Stock {
    private final String name;
    private final String price;
    private final String quantity;
    private final String promotion;

    public Stock(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = checkQuantity(quantity);
        this.promotion = promotion;
    }

    private String checkQuantity(String quantity) {
        if (quantity.equals("0")) {
            return "재고 없음";
        }
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }
}

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

    public void addNameAndPrice(Stock stock, StringBuilder builder) {
        try {
            int price = Integer.parseInt(stock.getPrice());
            builder.append("- ").append(stock.getName()).append(" ")
                    .append(String.format("%,d원", price)).append(" ");
        } catch (NumberFormatException e) {
            System.out.println("가격 형식이 잘못되었습니다 : " + stock.getPrice());
        }
    }

    public void addQuantity(Stock stock, StringBuilder builder) {
        if ("재고 없음".equals(stock.getQuantity())) {
            builder.append(stock.getQuantity());
        }

        if (!"재고 없음".equals(stock.getQuantity())) {
            builder.append(stock.getQuantity()).append("개");
        }
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

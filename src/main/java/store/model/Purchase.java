package store.model;

public class Purchase {
    private final String  productName;
    private final int productQuantity;

    public Purchase(String productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}

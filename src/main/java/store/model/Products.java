package store.model;

import java.util.List;

public class Products {
    private final List<Stock> products;

    public Products(List<Stock> products) {
        this.products = products;
    }

    public List<Stock> getProducts() {
        return products;
    }
}

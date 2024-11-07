package store.controller;

import store.model.Products;
import store.model.Promotions;
import store.util.ProductsFileConverter;
import store.view.OutputView;

public class StoreController {
    private final OutputView outputView;

    public StoreController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        start();
    }

    private void start() {
        outputView.showStartComment();
        ProductsFileConverter productsFileConverter = new ProductsFileConverter();
        Products products = productsFileConverter.loadProducts();
        Promotions promotions = new Promotions();
        outputView.showProducts(products, promotions);
    }
}

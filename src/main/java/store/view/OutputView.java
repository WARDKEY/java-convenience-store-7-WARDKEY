package store.view;

import store.model.Products;
import store.model.Promotions;

public class OutputView {
    public void showStartComment() {
        System.out.println("안녕하세요 W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public void showProducts(Products products, Promotions promotions) {
        products.getProducts().stream()
                .map(stock -> {
                    StringBuilder builder = new StringBuilder();
                    stock.addNameAndPrice(stock, builder);
                    stock.addQuantity(stock, builder);
                    promotions.addPromotion(stock, builder);
                    return builder.toString();
                })
                .forEach(System.out::println);
    }
}

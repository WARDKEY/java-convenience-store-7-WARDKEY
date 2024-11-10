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

    public void showRequestProductAndQuantity() {
        System.out.println();
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2], [감자칩-1])");
    }

    public void showRequestMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public void showRequestPartialPayment(String productName, int nonDiscountedQuantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName,
                nonDiscountedQuantity);
    }

    public void showPromotionAdditional(String productName, int additionalQuantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName, additionalQuantity);
    }
}

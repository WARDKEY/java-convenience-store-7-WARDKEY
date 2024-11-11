package store.view;

import store.model.Products;
import store.model.Promotions;
import store.model.Receipt;

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
        System.out.println();
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public void showRequestPartialPayment(String productName, int nonDiscountedQuantity) {
        System.out.println();
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName,
                nonDiscountedQuantity);
    }

    public void showPromotionAdditional(String productName, int additionalQuantity) {
        System.out.println();
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", productName, additionalQuantity);
    }

    public void showReceipt(Receipt receipt) {
        System.out.println();
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t\t\t수량\t\t금액");
        receipt.getPurchasedProducts().forEach((productName, quantity) -> {
            int price = receipt.getProductPrices().get(productName);
            int totalPrice = price * quantity;
            System.out.printf("%s\t\t\t\t%d\t\t%,d%n", productName, quantity, totalPrice);
        });
        System.out.println("=============증\t\t정===============");
        receipt.getFreeProducts().forEach((productName, quantity) -> {
            System.out.printf("%s\t\t\t\t%d%n", productName, quantity);
        });
        System.out.println("====================================");
        System.out.printf("총구매액\t\t\t\t%d\t\t%,d%n",
                receipt.getPurchasedProducts().values().stream().mapToInt(Integer::intValue).sum(),
                receipt.getTotalPurchaseAmount());
        System.out.printf("행사할인\t\t\t\t\t\t-%,d%n", receipt.getTotalDiscountAmount());
        System.out.printf("멤버십할인\t\t\t\t\t\t-%,d%n", receipt.getMembershipDiscountAmount());
        System.out.printf("내실돈\t\t\t\t\t\t%,d%n", receipt.getFinalAmount());
    }

    public void showRequestContinueShopping() {
        System.out.println();
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }
}

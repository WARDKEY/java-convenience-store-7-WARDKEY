package store.model;

import java.util.Arrays;
import java.util.List;

public class Purchase {
    private String productName;
    private int productQuantity;
    private final String purchase;

    public Purchase(String purchase, Products products) {
        validatePurchase(purchase, products);
        this.purchase = purchase;
    }

    private void validatePurchase(String purchase, Products products) {

        if (purchase.isBlank() || purchase == null) {
            throw new IllegalArgumentException("[ERROR] 상품을 입력해주세요.");
        }

        if (purchase.endsWith(",")) {
            throw new IllegalArgumentException("[ERROR] 상품을 이어서 입력해주세요.");
        }

        List<String> purchaseProducts = Arrays.stream(purchase.split(",")).toList();

        for (String purchaseProduct : purchaseProducts) {
            if (!(purchaseProduct.startsWith("[") && purchaseProduct.endsWith("]"))) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
            }

            if (!purchaseProduct.contains("-")) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
            }

            String productName = purchaseProduct.replaceAll("\\[([^\\-]+)-.*\\]", "$1").trim();
            String productQuantity = purchaseProduct.replaceAll(".*-(\\d+)\\]", "$1").trim();

            if (productName.isBlank()) {
                throw new IllegalArgumentException("[ERROR] 상품을 입력해주세요.");
            }

            int quantity;
            if (productQuantity.isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 수량을 입력해주세요.");
            }
            quantity = Integer.parseInt(productQuantity);

            if (quantity == 0) {
                throw new IllegalArgumentException("[ERROR] 수량을 입력해주세요.");
            }

            if (quantity < 0) {
                throw new IllegalArgumentException("[ERROR] 수량은 음수로 입력할 수 없습니다.");
            }

            boolean productExists = products.getProducts().stream()
                    .anyMatch(stock -> stock.getName().equals(productName));

            if (!productExists) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }

            products.getProducts().stream()
                    .filter(stock -> stock.getName().equals(productName))
                    .findFirst()
                    .ifPresent(stock -> {
                        if (quantity > Integer.parseInt(stock.getQuantity())) {
                            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
                        }
                    });
        }
    }
}
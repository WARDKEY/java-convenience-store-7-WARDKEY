package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.model.Membership;
import store.model.Products;
import store.model.Promotions;
import store.model.Purchase;
import store.util.file.ProductsFileConverter;
import store.util.file.PromotionsFileConverter;
import store.view.OutputView;

public class StoreController {
    private final OutputView outputView;
    private Purchase purchase;
    private Products products;
    private Promotions promotions;

    public StoreController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        start();
    }

    private void start() {
        outputView.showStartComment();
        loadProducts();

        loadPromotions();

        outputView.showProducts(products, promotions);

        outputView.showRequestProductAndQuantity();

        getPurchaseInput();

        outputView.showRequestMembership();
        while (true) {
            try {
                String membershipStatus = Console.readLine().trim();
                Membership membership = new Membership(membershipStatus);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    private void getPurchaseInput() {
        while (true) {
            try {
                String purchaseProducts = Console.readLine().trim();
                purchase = new Purchase(purchaseProducts, products);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 상품 개수는 숫자를 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Promotions loadPromotions() {
        PromotionsFileConverter promotionsFileConverter = new PromotionsFileConverter();
        Promotions promotions = promotionsFileConverter.loadFile();
        return promotions;
    }

    private Products loadProducts() {
        ProductsFileConverter productsFileConverter = new ProductsFileConverter();
        Products products = productsFileConverter.loadFile();
        return products;
    }
}

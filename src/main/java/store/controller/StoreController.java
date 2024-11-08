package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.model.Membership;
import store.model.Products;
import store.model.Promotions;
import store.model.Purchase;
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
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2], [감자칩-1])");

        while (true) {
            try {
                String purchaseProducts = Console.readLine().trim();
                Purchase purchase = new Purchase(purchaseProducts, products);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 상품 개수는 숫자를 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
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
}

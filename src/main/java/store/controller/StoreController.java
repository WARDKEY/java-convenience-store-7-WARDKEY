package store.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import store.model.Discount;
import store.model.Membership;
import store.model.Products;
import store.model.Promotions;
import store.model.Purchase;
import store.model.Receipt;
import store.model.Stock;
import store.model.replyStatus;
import store.util.file.ProductsFileConverter;
import store.util.file.PromotionsFileConverter;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final OutputView outputView;
    private final InputView inputView;
    private Purchase purchase;
    private Products products;
    private Promotions promotions;
    private Membership membership;

    public StoreController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        loadProducts();
        loadPromotions();
        while (true) {
            start();
            if (!handleContinueShopping()) {
                break;
            }
        }
    }

    private void start() {
        outputView.showStartComment();
        outputView.showProducts(products, promotions);
        outputView.showRequestProductAndQuantity();
        getPurchaseInput();
        validatePromotions();
        outputView.showRequestMembership();
        getMembershipInput();
        Receipt receipt = new Receipt(purchase, products, promotions, membership.isMember());
        outputView.showReceipt(receipt);
    }

    private Products loadProducts() {
        ProductsFileConverter productsFileConverter = new ProductsFileConverter();
        products = productsFileConverter.loadFile();
        return products;
    }

    private Promotions loadPromotions() {
        PromotionsFileConverter promotionsFileConverter = new PromotionsFileConverter();
        promotions = promotionsFileConverter.loadFile();
        return promotions;
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

    private void validatePromotions() {
        LocalDate today = DateTimes.now().toLocalDate();
        purchase.getPurchases().forEach((productName, productQuantity) -> {
            int quantity = Integer.parseInt(productQuantity);
            List<Stock> stocks = products.findStocksByName(productName);
            Discount applicableDiscount = findApplicableDiscount(stocks, today);
            if (applicableDiscount != null) {
                handleProductPromotion(applicableDiscount, stocks, productName, quantity);
                return;
            }

            reduceStockWithoutPromotion(stocks, quantity);
        });
    }

    private Discount findApplicableDiscount(List<Stock> stocks, LocalDate today) {
        for (Stock stock : stocks) {
            Discount discount = promotions.findPromotionByStock(stock);
            if (isValidPromotion(discount, today)) {
                return discount;
            }
        }
        return null;
    }

    private boolean isValidPromotion(Discount discount, LocalDate today) {
        if (discount == null) {
            return false;
        }
        return !today.isBefore(discount.getStartDate()) && !today.isAfter(discount.getEndDate());
    }

    private void handleProductPromotion(Discount discount, List<Stock> stocks, String productName, int quantity) {
        int buyQuantity = Integer.parseInt(discount.getBuy());
        int additionalQuantity = Integer.parseInt(discount.getGet());

        int totalRequiredQuantity = buyQuantity + additionalQuantity;

        if (quantity >= totalRequiredQuantity) {
            int promotionCount = quantity / totalRequiredQuantity;
            int freeProducts = promotionCount * additionalQuantity;
            int availablePromotionStock = getAvailablePromotionStock(stocks, discount.getName());
            int addtionalFreeProudctsCount = Math.min(freeProducts, availablePromotionStock);

            if (addtionalFreeProudctsCount > 0) {
                reducePromotionStock(stocks, discount.getName(), addtionalFreeProudctsCount);
                purchase.addFreeProducts(productName, addtionalFreeProudctsCount);
            }

            int totalPromotionApplied = promotionCount * buyQuantity;
            reduceGeneralStock(stocks, totalPromotionApplied);

            if (addtionalFreeProudctsCount < freeProducts) {
                int remainingFreeItems = freeProducts - addtionalFreeProudctsCount;
                outputView.showRequestPartialPayment(productName, remainingFreeItems);
                replyStatus partialReply = inputView.invalidReply();

                if (partialReply == replyStatus.Y) {
                    reduceGeneralStock(stocks, remainingFreeItems);
                }

                return;
            }

            return;
        }

        if (quantity >= buyQuantity) {
            int missingGetCount = additionalQuantity;
            outputView.showAdditionalPromotion(productName, missingGetCount);

            replyStatus reply;
            while (true) {
                try {
                    reply = inputView.invalidReply();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (reply == replyStatus.Y) {
                purchase.updateQuantity(productName, quantity + missingGetCount);

                int availablePromotionStock = getAvailablePromotionStock(stocks, discount.getName());
                int additionalFreeProducts = Math.min(additionalQuantity, availablePromotionStock);

                if (additionalFreeProducts > 0) {
                    reducePromotionStock(stocks, discount.getName(), additionalFreeProducts);
                    purchase.addFreeProducts(productName, additionalFreeProducts);
                }

                reduceGeneralStock(stocks, buyQuantity);
                return;
            }
            reduceGeneralStock(stocks, quantity);
            return;
        }
        reduceStockWithoutPromotion(stocks, quantity);
    }

    private int getAvailablePromotionStock(List<Stock> stocks, String promotionName) {
        return stocks.stream()
                .filter(stock -> promotionName.equals(stock.getPromotion()))
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    private void reducePromotionStock(List<Stock> stocks, String promotionName, int quantity) {
        for (Stock stock : stocks) {
            if (promotionName.equals(stock.getPromotion())) {
                int available = stock.getQuantity();
                if (available >= quantity) {
                    stock.reduceStock(quantity);
                    break;
                }
                stock.reduceStock(available);
                quantity -= available;
            }
        }
    }

    private void reduceGeneralStock(List<Stock> stocks, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getPromotion() == null) {
                int available = stock.getQuantity();
                if (available >= quantity) {
                    stock.reduceStock(quantity);
                    break;
                }
                stock.reduceStock(available);
                quantity -= available;
            }
        }
    }

    private void getMembershipInput() {
        replyStatus membershipstatus = inputView.getMembershipReply();
        membership = new Membership(membershipstatus);
    }

    private void reduceStockWithoutPromotion(List<Stock> stocks, int quantity) {
        int remainingQuantity = quantity;
        for (Stock stock : stocks) {
            if (stock.getPromotion() != null) {
                continue;
            }
            int stockQuantity = stock.getQuantity();
            int purchasable = Math.min(remainingQuantity, stockQuantity);
            stock.reduceStock(purchasable);
            remainingQuantity -= purchasable;
            if (remainingQuantity == 0) {
                break;
            }
        }
    }

    private boolean handleContinueShopping() {
        outputView.showRequestContinueShopping();
        replyStatus reply = inputView.getContinueShoppingReply();
        if (reply == replyStatus.Y) {
            return true;
        }
        return false;
    }

}

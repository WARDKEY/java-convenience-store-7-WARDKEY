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

    public StoreController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
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

        validatePromotions();

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

    private void validatePromotions() {
        LocalDate today = DateTimes.now().toLocalDate();
        purchase.getPurchases().forEach((productName, productQuantity) -> {
            int quantity = Integer.parseInt(productQuantity);
            List<Stock> stocks = products.findStocksByName(productName); // 해당 상품의 재고 가져옴
            Discount applicableDiscount = findApplicableDiscount(stocks, today); // 적용 가능한 할인 찾음
            if (applicableDiscount != null) {
                handleProductPromotion(applicableDiscount, stocks, productName, quantity); // 할인 적용
            }
        });
    }

    private Discount findApplicableDiscount(List<Stock> stocks, LocalDate today) {
        for (Stock stock : stocks) {
            Discount discount = promotions.findPromotionByStock(stock); // 프로모션 찾음
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
        // 할인 기간 내에 있는지 확인
        return !today.isBefore(discount.getStartDate()) && !today.isAfter(discount.getEndDate());
    }

    private void handleProductPromotion(Discount discount, List<Stock> stocks, String productName, int quantity) {
        quantity = handleAdditionalPromotionQuantity(discount, productName, quantity); // 추가 프로모션 수량
        applyPromotion(discount, stocks, productName, quantity); // 프로모션 적용
    }

    private int handleAdditionalPromotionQuantity(Discount discount, String productName, int quantity) {
        int requiredQuantity = Integer.parseInt(discount.getBuy()); // 구매해야 할 수량
        int additionalQuantity = Integer.parseInt(discount.getGet()); // 추가로 받을 수량

        if (quantity < requiredQuantity) { // 현재 수량이 조건 미달인 경우
            outputView.showPromotionAdditional(productName, additionalQuantity); // 추가 수량 요청 메시지 출력
            replyStatus reply = inputView.getPromotionReply();
            if (reply == replyStatus.Y) { // 'Y' 선택 시
                int additionalNeeded = requiredQuantity - quantity; // 추가로 필요한 수량 계산
                quantity += additionalNeeded; // 구매 수량 증가
                purchase.updateQuantity(productName, quantity); // 구매 내역 업데이트
            }
        }
        return quantity; // 업데이트된 구매 수량 반환
    }

    private void applyPromotion(Discount discount, List<Stock> stocks, String productName, int quantity) {
        int requiredQuantity = Integer.parseInt(discount.getBuy()); // 구매해야 할 수량
        int additionalQuantity = Integer.parseInt(discount.getGet()); // 추가로 받을 수량

        int applicablePromotionCount = quantity / requiredQuantity; // 적용 가능한 프로모션 수
        int totalDiscountedQuantity = applicablePromotionCount * additionalQuantity; // 총 추가 받을 수량

        // 프로모션이 적용된 재고에서 우선 할인 적용
        for (Stock stock : stocks) {
            if (stock.getPromotion().equals(discount.getName())) {
                int discountedQuantityFromPromotion = Math.min(totalDiscountedQuantity,
                        Integer.parseInt(stock.getQuantity())); // 프로모션 재고에서 할인 가능한 수량
                stock.reduceStock(discountedQuantityFromPromotion); // 재고 감소
                purchase.addFreeProducts(productName, discountedQuantityFromPromotion); // 무료 상품 추가
                totalDiscountedQuantity -= discountedQuantityFromPromotion; // 남은 할인 수량 갱신
                break; // 프로모션 재고에서 할인 적용 완료
            }
        }

        // 프로모션 재고가 부족할 경우 일반 재고에서 추가 할인 적용
        if (totalDiscountedQuantity > 0) {
            for (Stock stock : stocks) {
                if (stock.getPromotion().isEmpty()) { // 일반 재고인 경우
                    if (totalDiscountedQuantity > Integer.parseInt(stock.getQuantity())) {
                        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
                    }
                    stock.reduceStock(totalDiscountedQuantity); // 재고 감소
                    purchase.addFreeProducts(productName, totalDiscountedQuantity); // 무료 상품 추가
                    totalDiscountedQuantity = 0; // 남은 할인 수량 없음
                    break;
                }
            }
        }

        int remainingQuantity = quantity - (applicablePromotionCount * requiredQuantity); // 남은 구매 수량 계산
        handlePartialPayment(stocks, productName, remainingQuantity); // 남은 수량 결제 처리함
        int finalQuantity = quantity + purchase.getFreeProducts().getOrDefault(productName, 0); // 최종 구매 수량 계산
        purchase.updateQuantity(productName, finalQuantity); // 구매 내역 업데이트
    }

    private void handlePartialPayment(List<Stock> stocks, String productName, int remainingQuantity) {
        if (remainingQuantity <= 0) { // 남은 수량이 없으면 종료
            return;
        }

        // 일반 재고의 총 수량 계산
        int totalGeneralStock = stocks.stream()
                .filter(stock -> stock.getPromotion().isEmpty())
                .mapToInt(quantity -> Integer.parseInt(quantity.getQuantity()))
                .sum();

        if (remainingQuantity > totalGeneralStock) { // 일반 재고가 부족한 경우
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }

        // 사용자에게 부분 결제 여부 묻기
        outputView.showRequestPartialPayment(productName, remainingQuantity);
        replyStatus reply = inputView.partialPaymentReply();
        if (reply == replyStatus.N) { // 'N' 선택 시 프로모션 조건을 만족하는 만큼만 구매
            Discount discount = findApplicableDiscount(stocks, LocalDate.now());
            if (discount != null) {
                int applicablePromotionSets = remainingQuantity / Integer.parseInt(discount.getBuy());
                int updatedQuantity = applicablePromotionSets * Integer.parseInt(discount.getBuy());
                purchase.updateQuantity(productName, updatedQuantity); // 구매 내역 업데이트
            }
            return;
        }

        // 'Y' 선택 시 일반 재고에서 남은 수량을 구매
        for (Stock stock : stocks) {
            if (stock.getPromotion().isEmpty()) { // 일반 재고인 경우
                if (remainingQuantity <= 0) { // 구매할 수량이 없으면 종료
                    break;
                }
                int purchasable = Math.min(remainingQuantity, Integer.parseInt(stock.getQuantity()));
                stock.reduceStock(purchasable);
                remainingQuantity -= purchasable;
            }
        }
    }

    // 상품 구매 입력
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

    // 프로모션 파일 로드
    private Promotions loadPromotions() {
        PromotionsFileConverter promotionsFileConverter = new PromotionsFileConverter();
        promotions = promotionsFileConverter.loadFile();
        return promotions;
    }

    // 상품 파일 로드
    private Products loadProducts() {
        ProductsFileConverter productsFileConverter = new ProductsFileConverter();
        products = productsFileConverter.loadFile();
        return products;
    }
}

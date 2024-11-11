package store.util.message;

public enum Error {

    ERROR_INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ERROR_PRODUCT_NOT_EXIST("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    ERROR_QUANTITY_EXCEEDS_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ERROR_INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String description;

    Error(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

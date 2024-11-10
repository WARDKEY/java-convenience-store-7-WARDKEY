package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.replyStatus;


public class InputView {

    public replyStatus getPromotionReply() {
        return getYesOrNoInput("[ERROR] 프로모션 추가 여부를 입력해주세요.", "[ERROR] 프로모션 추가 여부는 \"Y\"또는 \"N\"을 입력해주세요.");
    }

    public replyStatus partialPaymentReply() {
        return getYesOrNoInput("[ERROR] 일부 수량 결제 여부를 입력해주세요.", "[ERROR] 일부 수량 결제 여부는 \"Y\"또는 \"N\"을 입력해주세요.");
    }

    public replyStatus getMembershipReply() {
        return getYesOrNoInput("[ERROR] 멤버십 할인 여부를 입력해주세요.", "[ERROR] 멤버십 할인 여부는 \"Y\"또는 \"N\"을 입력해주세요.");
    }

    public replyStatus getContinueShoppingReply() {
        return getYesOrNoInput("[ERROR] 추가 구매 여부를 입력해주세요.", "[ERROR] 추가 구매 여부는 \"Y\"또는 \"N\"을 입력해주세요.");
    }

    private replyStatus getYesOrNoInput(String blankMessage, String invalidMessage) {
        while (true) {
            try {
                String input = Console.readLine().trim();
                return parseYesNoInput(input, blankMessage, invalidMessage);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private replyStatus parseYesNoInput(String input, String blankMessage, String invalidMessage) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(blankMessage);
        }
        return replyStatus.fromString(input);
    }
}

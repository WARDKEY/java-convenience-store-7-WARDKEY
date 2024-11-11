package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.replyStatus;


public class InputView {

    public replyStatus invalidReply() {
        return getYesOrNoInput("[ERROR] 입력이 존재하지 않습니다.", "[ERROR] \"Y\"또는 \"N\"을 입력해주세요.");
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
        replyStatus status = replyStatus.fromString(input);
        if (status == null) {
            throw new IllegalArgumentException(invalidMessage);
        }
        return status;
    }
}

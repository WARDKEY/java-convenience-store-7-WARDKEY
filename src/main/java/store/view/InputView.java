package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.replyStatus;
import store.util.message.Error;


public class InputView {

    public replyStatus invalidReply() {
        return getYesOrNoInput(Error.ERROR_INVALID_INPUT.getDescription(), Error.ERROR_INVALID_INPUT.getDescription());
    }

    public replyStatus getMembershipReply() {
        return getYesOrNoInput(Error.ERROR_INVALID_INPUT.getDescription(), Error.ERROR_INVALID_INPUT.getDescription());
    }

    public replyStatus getContinueShoppingReply() {
        return getYesOrNoInput(Error.ERROR_INVALID_INPUT.getDescription(), Error.ERROR_INVALID_INPUT.getDescription());
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

package store.model;

public enum replyStatus {
    Y, N;

    public static replyStatus fromString(String input) {
        if (input.equals("Y")) {
            return Y;
        }
        if (input.equals("N")) {
            return N;
        }
        throw new IllegalArgumentException("[ERROR] \"Y\"또는 \"N\"을 입력해주세요.");
    }
}

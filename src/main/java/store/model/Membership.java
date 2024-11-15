package store.model;

public class Membership {
    private final replyStatus membershipStatus;

    public Membership(replyStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public boolean isMember() {
        return membershipStatus == replyStatus.Y;
    }
}

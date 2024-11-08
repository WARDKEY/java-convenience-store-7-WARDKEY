package store.model;

public class Membership {
    private final String membershipStatus;

    public Membership(String membershipStatus) {
        validateMembership(membershipStatus);
        this.membershipStatus = membershipStatus;
    }

    private void validateMembership(String membershipStatus) {
        isBlankMembershipStatus(membershipStatus);

        invalidCharacter(membershipStatus);
    }

    private void isBlankMembershipStatus(String membershipStatus) {
        if (membershipStatus == null || membershipStatus.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 멤버십 할인 여부를 입력해주세요.");
        }
    }

    private void invalidCharacter(String membershipStatus) {
        if (!(membershipStatus.equals("Y") || membershipStatus.equals("N"))) {
            throw new IllegalArgumentException("[ERROR] 멤버십 할인 여부는 \"Y\"또는 \"N\"을 입력해주세요.");
        }
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }
}

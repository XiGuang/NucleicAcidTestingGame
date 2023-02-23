package NucleicAcidTesting.game;

public class UserInfo {
    static String userId;

    public static void setUserId(String userId) {
        UserInfo.userId = userId;
    }

    public static String getUserId() {
        return userId;
    }
}

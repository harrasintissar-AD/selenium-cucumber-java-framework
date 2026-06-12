package utils;

public class TestUserStorage {

    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(
            String username) {

        TestUserStorage.username =
                username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(
            String password) {

        TestUserStorage.password =
                password;
    }
}
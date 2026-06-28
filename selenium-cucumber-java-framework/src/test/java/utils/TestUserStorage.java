package utils;

/**
 * Thread-safe storage for test user credentials in parallel execution.
 * Uses ThreadLocal to ensure each thread maintains its own user context.
 *
 * Prevents data leaks between parallel test scenarios where different users
 * might be logging in simultaneously.
 */
public class TestUserStorage {

    // ThreadLocal ensures each thread has its own username and password
    private static final ThreadLocal<String> username = new ThreadLocal<>();
    private static final ThreadLocal<String> password = new ThreadLocal<>();

    public static String getUsername() {
        return username.get();
    }

    public static void setUsername(String user) {
        username.set(user);
    }

    public static String getPassword() {
        return password.get();
    }

    public static void setPassword(String pass) {
        password.set(pass);
    }

    /**
     * Clears thread-local data to prevent memory leaks in thread pools.
     * Call this in @After hook to ensure cleanup between scenarios.
     */
    public static void clear() {
        username.remove();
        password.remove();
    }
}
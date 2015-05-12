package jp.geekschool.web.security;

public class AuthenticationHolder {

    private static ThreadLocal<Authentication> storage = new InheritableThreadLocal<Authentication>() {
        @Override
        protected Authentication initialValue() {
            return new Authentication();
        }
    };

    public static Authentication getAuthentication() {
        Authentication authentication = storage.get();
        if (authentication == null) {
            authentication = new Authentication();
            storage.set(authentication);
        }
        return authentication;
    }

    public static void setAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication is null");
        }
        storage.set(authentication);
    }

    public static void clearAuthentication() {
        // clear authentication object
        storage.set(null);
    }

}

package hv.hoviet.itpostbackend.util;

import hv.hoviet.itpostbackend.model.User;

public class AuthorityUtil {
    public static Boolean hasRole(String role, User user) {
        return user.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority().equals(role))
                .count() > 0;
    }
}

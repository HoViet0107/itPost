package hv.hoviet.itpostbackend.util;

import hv.hoviet.itpostbackend.model.Role;
import hv.hoviet.itpostbackend.model.User;
import hv.hoviet.itpostbackend.model.enums.EnumRole;
import hv.hoviet.itpostbackend.respository.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthorityUtil {
    @Value("${itPost.app.jwtSecret}")
    private String secretKey;

    public Set extractRoles(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Set roles = new HashSet<>();
        List authorities = (List) claims.get("authorities");

        if (authorities != null) {
            for (Object authority : authorities) {
                roles.add(authority);
            }
        }
        return roles;
    }
}

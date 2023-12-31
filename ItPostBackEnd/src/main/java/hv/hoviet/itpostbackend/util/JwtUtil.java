package hv.hoviet.itpostbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Value("${itPost.app.jwtSecret}")
    private String jwtSecret;

    @Value("${itPost.app.jwtExpirationMs}")
    private int jwtExpirationMs; // miliseconds
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // tạo token dự trên thông tin người dùng userDetails
    public String generateToken(UserDetails userDetails){
        // tạo một Map để chứa thông tin người dùng
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("authorities", authorities
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList()));
        return doGenerateToken(claims, userDetails.getUsername());
    }
    public String doGenerateToken(Map<String, Object> extractClaims,String subject){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    validate token
    public boolean isTokenValid (String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username == userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

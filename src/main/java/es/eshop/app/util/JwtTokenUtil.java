package es.eshop.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtTokenUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long timeExpired;


    /**
     * Create token
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", authentication.getPrincipal());
        claims.put("roles", authentication.getAuthorities());

        return Jwts.builder()
                .setSubject((String) authentication.getPrincipal())
                .setExpiration(new Date(System.currentTimeMillis() + (timeExpired * 1000)))
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    /**
     * Get authentication by token
     * @param token
     * @return
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            return new UsernamePasswordAuthenticationToken(getClaims(token).getSubject(), null, Collections.emptyList());
        } catch (JwtException e) {
            log.error("Invalid token");
            // TODO: EXCEPTION
            return null;
        }
    }

    /**
     * Get email from token
     * @param token
     * @return
     */
    public String getEmailFromToken(String token){
        return ((Function<Claims, String>) Claims::getSubject).apply(getClaims(token));
    }

    /**
     * validate token
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validatedToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check is token expired
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = ((Function<Claims, Date>) Claims::getExpiration).apply(getClaims(token));
        return expiration.before(new Date());
    }

    /**
     * Get claims for token
     * @param token
     * @return
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

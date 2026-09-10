package com.happypets.app_veterinaria_backend.common.infrastructure.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

/**
 * Principal class to manage the configurations of the jwt
 *
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-window}")
    private Long refreshWindow;

    /**
     * Principal method to generate the token with the authorities and claims
     *
     */
    public String generateToken(UserDetails userDetails, Long userId, String name,
                                Set<String> roles, Set<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId.toString());
        claims.put("name", name);
        claims.put("roles", roles);
        claims.put("permissions", permissions);
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());

        return generateToken(claims, userDetails.getUsername());
    }

    /**
     * Principal method to generate the base attributes of the jwt token
     *
     */
    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignature())
                .compact();
    }

    /**
     * Method to extract the email claim of the user
     *
     */
    public String getEmail(String token) {
        return getUsername(token);
    }

    /**
     * Method to get the user id claim
     *
     */
    public UUID getUserId(String token) {
        String userIdStr = getClaim(token, claims -> claims.get("userId", String.class));
        return userIdStr != null ? UUID.fromString(userIdStr) : null;
    }

    /**
     * Method to get the name of the user claim
     *
     */
    public String getName(String token) {
        return getClaim(token, claims -> claims.get("name", String.class));
    }

    /**
     * Method to get the roles claims
     *
     */
    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {
        List<String> roles = getClaim(token, claims -> claims.get("roles", List.class));
        return roles != null ? new HashSet<>(roles) : Collections.emptySet();
    }

    /**
     * Method to get the permissions
     *
     */
    @SuppressWarnings("unchecked")
    public Set<String> getPermissions(String token) {
        List<String> permissions = getClaim(token, claims -> claims.get("permissions", List.class));
        return permissions != null ? new HashSet<>(permissions) : Collections.emptySet();
    }

    /**
     * Principal method to get the signature of verify the signature
     *
     */
    private SecretKey getSignature() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parser().verifyWith(getSignature()).build().parseSignedClaims(token).getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or malformed", e);
        }
    }

    /**
     * Method to get the username
     *
     */
    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    /**
     * Method to get the claims
     *
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsMapper) {
        Claims allClaims = getAllClaims(token);
        return claimsMapper.apply(allClaims);
    }

    /**
     * Method to get the expiration date
     *
     */
    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Method to verify if the token was expired
     *
     */
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * Method to verify if the token can be renewed
     *
     */
    public boolean canBeTokenRenewed(String token) {
        Date expiration = getExpirationDate(token);
        long timeSinceExpiration = System.currentTimeMillis() - expiration.getTime();
        return timeSinceExpiration <= refreshWindow;
    }

    /**
     * Method to renew the token preserving all custom claims
     */
    public String renewToken(String oldToken) {
        if (!canBeTokenRenewed(oldToken)) {
            throw new RuntimeException("Token cannot be renewed");
        }

        Claims oldClaims = getAllClaims(oldToken);

        //Copy de old claims
        Map<String, Object> claims = new HashMap<>(oldClaims);

        // Remove the claims of expiration data (for renew)
        claims.remove(Claims.SUBJECT);
        claims.remove(Claims.ISSUED_AT);
        claims.remove(Claims.EXPIRATION);

        return generateToken(claims, oldClaims.getSubject());
    }

    /**
     * Method to verify if the token is valid
     *
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername());
    }
}
package com.it.securityexample.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtService {

    @Value("${security.jwt.secret}")
    private String JWT_SECRET;

    @Value("${security.jwt.expiration-time}")
    private int JWT_EXPIRATION_TIME;

    /**
     * Generates a JWT token for the given userName.
     * 
     * 
     * @param userName
     * @return
     */
    public String generateToken(String userName) {
        // Prepare claims for the token
        Map<String, Object> claims = new HashMap<>();
        // Build JWT token with claims, subject, issued time, expiration time, and
        // signing algorithm
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Extracts the userName from the JWT token.
     * 
     * @param token
     * @return The userName contained in the token.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the JWT token against the UserDetails.
     * 
     * @param token
     * @param userDetails
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        // Extract username from token and check if it matches UserDetails' username
        final String jwtUserName = extractUserName(token);
        final boolean isExpired = isTokenExpired(token);
        final boolean isSameUsername = jwtUserName.equals(userDetails.getUsername());
        return (isSameUsername && !isExpired);
    }

    /**
     * Creates a signing key from the base64 encoded secret.
     * returns a Key object for signing the JWT.
     * 
     * @return
     */
    private Key getSignKey() {
        // Decode the base64 encoded secret key and return a Key object
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    /**
     * Extracts a specific claim from the JWT token.
     * claimResolver A function to extract the claim.
     * 
     * @param <T>
     * @param token
     * @param claimResolver
     * @return The value of the specified claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimResolver.apply(claims);
    }

    /**
     * Checks if the JWT token is expired.
     * 
     * @param token
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

}

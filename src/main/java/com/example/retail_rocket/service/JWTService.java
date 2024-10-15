package com.example.retail_rocket.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private String secretKey;
    private static final String TOKEN = "token";
    private static final String EXPIRATION = "expiration";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }

    public Map<String,Object> generateTokenForUser(String username) {
        Map<String,Object> claims = new HashMap<>();
        Map<String,Object> tokenInfo = new HashMap<>();
        long currentTimeInMillisSecond = System.currentTimeMillis();
        long expirationTimeInMillisSecond = currentTimeInMillisSecond +60*60*30 ;

        String token =  Jwts.builder().claims().add(claims)
                .subject(username)
                .issuedAt(new Date(currentTimeInMillisSecond))
                .expiration(new Date(expirationTimeInMillisSecond))
                .and()
                .signWith(getKey())
                .compact();
          tokenInfo.put(TOKEN,token);
          tokenInfo.put(EXPIRATION,expirationTimeInMillisSecond);
          return tokenInfo;
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractuserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractuserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
}

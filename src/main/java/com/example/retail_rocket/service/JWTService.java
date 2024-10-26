package com.example.retail_rocket.service;

import com.example.retail_rocket.ExceptionHandler.Tokenvalidation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private static final String TOKEN = "token";
    private static final String EXPIRATION = "expiration";

    public JWTService(){
        //As this was generating new secret key everytime,
        // so whenever u restart the server the old token will be invalid
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//            System.out.println("Secret key: "+secretKey);
//        }catch (Exception ex){
//            throw new RuntimeException();
//        }
    }

    public Map<String,Object> generateTokenForUser(String username, Authentication authentication) {
        Map<String,Object> claims = new HashMap<>();
        Map<String,Object> tokenInfo = new HashMap<>();
        claims.put("username",username);
        claims.put("roles",authentication.getAuthorities().stream().collect(Collectors.toList()));
        long currentTimeInMillisSecond = System.currentTimeMillis();
        long expirationTimeInMillisSecond = currentTimeInMillisSecond + 60*60*30*100 ;

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
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception exception){
            throw new Tokenvalidation("Token is not validated");
        }
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
        System.out.println("Inside validate filter--------------");
        final String username = extractuserName(token);
        if( username.equals(userDetails.getUsername())) {
            if (!isTokenExpired(token))
                return true;
            else
                throw new Tokenvalidation("Token expired");
        }
        else
            throw new Tokenvalidation("User not found");

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

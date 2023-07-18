package com.soloProject.server.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

// JWT를 생성하는 JwtTokenizer
@Component
public class JwtTokenizer { //JWT 를 생성하고 검증하는 역할을 수행하는 클래스
    @Getter
    @Value("${jwt.key}")
    private String secretKey;       //환경변수

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        //Plain Text 형태인 Secret Key의 byte[]를 Base64 형식의 문자열로 인코딩
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //인증된 사용자에게 JWT를 최초로 발급해 주기 위한 JWT 생성 메서드
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims) //JWT에 포함시킬 Custom Claims를 추가. Custom Claims에는 주로 인증된 사용자와 관련된 정보를 추가
                .setSubject(subject) //JWT에 대한 제목을 추가
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key) //서명을 위한 Key(java.security.Key) 객체를 설정
                .compact();
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }


    //JWT의 만료 일시를 지정하기 위한 메서드로 JWT 생성 시 사용
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        //getKeyFromBase64EncodedKey() 메서드는 JWT의 서명에 사용할 Secret Key를 생성
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);


        if (keyBytes.length < 32) { //길이늘려줌
            byte[] paddedKeyBytes = new byte[32];
            System.arraycopy(keyBytes, 0, paddedKeyBytes, 0, keyBytes.length);
            keyBytes = paddedKeyBytes;
        }

        //HMAC 알고리즘을 적용한 Key(java.security.Key) 객체를 생성
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }
}

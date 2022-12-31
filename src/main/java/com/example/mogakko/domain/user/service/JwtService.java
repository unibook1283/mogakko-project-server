package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Component
public class JwtService {

    @Value("${secretKey}")
    private String secretKey;

    private final Long accessTokenExpiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간
    private final Long refreshTokenExpiredTime = 1000 * 60L * 60L * 24L * 14L; // 유효시간 14일

    // 토큰 생성하는 메서드
    public String createToken(Long userId) { // 토큰에 담고싶은 값 파라미터로 가져오기
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .setSubject("accessToken") // 토큰 제목
                .claim("id", String.valueOf(userId)) // 토큰에 담을 데이터
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredTime)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // secretKey를 사용하여 해싱 암호화 알고리즘 처리
                .compact(); // 직렬화, 문자열로 변경
    }

    // 리프레시 토큰 생성하는 메서드
    public String createRefreshToken() {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .setSubject("refreshToken") // 토큰 제목
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    // 토큰에 담긴 정보를 가져오기 메서드
    public Map<String, Object> getInfo(String token) throws Exception {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token); // secretKey를 사용하여 복호화
        } catch(Exception e) {
            throw new Exception();
        }

        return claims.getBody();
    }

    // interceptor에서 토큰 유효성을 검증하기 위한 메서드
    public void checkValid(String token) {
        Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
    }
}

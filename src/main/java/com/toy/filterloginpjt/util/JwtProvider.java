package com.toy.filterloginpjt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.filterloginpjt.dto.SignInRequestDTO;
import com.toy.filterloginpjt.redis.RedisDao;
import com.toy.filterloginpjt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserRepository userRepository;
    private final RedisDao redisDao;
    private final ObjectMapper objectMapper;

    @Value("${spring.jwt.secret-key}")
    private String JWT_KEY;

    @Value("${spring.jwt.life.atk}")
    private Long ATK_LIFE;

    @Value("${spring.jwt.life.rtk}")
    private Long RTK_LIFE;


    // Jwt 생성
    public String createToken(PayLoad payLoad) {

        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8)); // UTF-8로 인코딩
        Long lifeTime = payLoad.getType().equals("ATK") ? ATK_LIFE : RTK_LIFE;
        String jwt = Jwts.builder()
                .setIssuer("JwtProject")
                .setSubject("JWT Token")
                .claim("id", payLoad.getId()) // 클레임과 PayLoad 멤버변수 명이 같아야합니다.
                .claim("email", payLoad.getEmail())
                .claim("type", payLoad.getType())
                .claim("authority", payLoad.getAuthority())
                .setExpiration(new Date(new Date().getTime() +  lifeTime))
                .signWith(secretKey).compact();

        if (payLoad.getType().equals("RTK")) {
            redisDao.setValues(payLoad.getEmail(), jwt, Duration.ofMillis(lifeTime));
        }

        return jwt;
    }

    // Jwt 유효성 검사를 위해 토큰에서 PayLoad 추출하기
    public PayLoad getPayLoad(String jwt) throws JsonProcessingException {
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println(claims);

        PayLoad payLoad = PayLoad.builder()
                .id(Long.valueOf((Integer) claims.get("id")))
                .email((String) claims.get("email"))
                .type((String) claims.get("type"))
                .authority((String) claims.get("authority"))
                .build();
        return payLoad;
    }

}

package kr.codesquad.secondhand.api.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import kr.codesquad.secondhand.api.jwt.config.JwtProperties;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    private void setKey() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    public Jwt createTokens(Map<String, Object> claims) {
        String accessToken = createToken(claims, getAccessTokenExpireDate());
        String refreshToken = createToken(new HashMap<>(), getRefreshTokenExpireDate());
        return new Jwt(accessToken, refreshToken);
    }

    private String createToken(Map<String, Object> claims, Date expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String reissueAccessToken(Map<String, Object> claims) {
        return createToken(claims, getAccessTokenExpireDate());
    }

    private Date getAccessTokenExpireDate() {
        return new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationTime());
    }

    private Date getRefreshTokenExpireDate() {
        return new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpirationTime());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(String token) { // TODO Date 타입이 시스템 시간이랑 일치하지 않을 수도 있는데, 관련해서 공부하고 처리 필요
        return getClaims(token).getExpiration();
    }
}

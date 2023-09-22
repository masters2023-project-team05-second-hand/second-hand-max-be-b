package kr.codesquad.secondhand.api.jwt.domain;

import lombok.Getter;

@Getter
public class Jwt {

    private final String accessToken;
    private final String refreshToken;
    private final Long refreshTokenExpirationTime;

    public Jwt(String accessToken, String refreshToken, Long refreshTokenExpirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}

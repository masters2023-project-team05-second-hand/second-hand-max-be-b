package kr.codesquad.secondhand.api.jwt.domain;

import lombok.Getter;

@Getter
public class Jwt {

    private final String accessToken;
    private final String refreshToken;

    public Jwt(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

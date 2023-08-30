package kr.codesquad.secondhand.api.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Jwt {

    private String accessToken;
    private String refreshToken;

}

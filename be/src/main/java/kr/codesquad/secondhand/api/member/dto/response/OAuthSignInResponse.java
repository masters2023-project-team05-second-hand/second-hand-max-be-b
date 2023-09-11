package kr.codesquad.secondhand.api.member.dto.response;

import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import lombok.Getter;

@Getter
public class OAuthSignInResponse {

    private final String accessToken;
    private final String refreshToken;

    private OAuthSignInResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static OAuthSignInResponse from(Jwt jwt) {
        return new OAuthSignInResponse(jwt.getAccessToken(), jwt.getRefreshToken());
    }
}

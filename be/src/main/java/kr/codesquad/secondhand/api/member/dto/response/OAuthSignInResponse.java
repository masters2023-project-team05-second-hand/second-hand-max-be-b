package kr.codesquad.secondhand.api.member.dto.response;

import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import lombok.Getter;

@Getter
public class OAuthSignInResponse {

    private final Jwt tokens;

    public OAuthSignInResponse(Jwt tokens) {
        this.tokens = tokens;
    }

    public static OAuthSignInResponse from(Jwt jwt) {
        return new OAuthSignInResponse(jwt);
    }
}

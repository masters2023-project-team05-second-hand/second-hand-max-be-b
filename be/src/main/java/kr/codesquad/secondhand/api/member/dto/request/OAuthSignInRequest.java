package kr.codesquad.secondhand.api.member.dto.request;

import lombok.Getter;

@Getter
public class OAuthSignInRequest {

    // Oauth 서버 인증용 Authorization Code
    private String accessCode;
}

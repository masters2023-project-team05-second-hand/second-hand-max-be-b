package kr.codesquad.secondhand.api.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthSignInRequest {

    // Oauth 서버 인증용 Authorization Code
    private String accessCode;
}

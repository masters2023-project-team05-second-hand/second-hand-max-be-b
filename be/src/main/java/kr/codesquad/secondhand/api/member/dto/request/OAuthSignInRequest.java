package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OAuthSignInRequest {

    @NotEmpty
    private String accessCode;
}

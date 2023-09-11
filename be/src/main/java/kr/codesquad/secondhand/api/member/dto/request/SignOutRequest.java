package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignOutRequest {

    @NotEmpty(message = "refresh token이 비어있습니다.")
    private String refreshToken;

}

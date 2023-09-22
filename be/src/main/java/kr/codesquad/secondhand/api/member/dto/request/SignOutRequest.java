package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignOutRequest {

    @NotEmpty(message = "refresh token이 비어있습니다.")
    private String refreshToken;

    public SignOutRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

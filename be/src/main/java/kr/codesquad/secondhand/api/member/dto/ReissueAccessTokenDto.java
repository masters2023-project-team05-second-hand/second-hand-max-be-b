package kr.codesquad.secondhand.api.member.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReissueAccessTokenDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {

        @NotEmpty(message = "refresh token이 비어있습니다.")
        private String refreshToken;

        public Request(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    public static class Response {

        private final String accessToken;

        public Response(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}

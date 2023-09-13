package kr.codesquad.secondhand.api.member.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

public class ReissueAccessTokenDto {

    @Getter
    public static class Request {

        @NotEmpty(message = "refresh token이 비어있습니다.")
        private String refreshToken;
    }

    @Getter
    public static class Response {

        private final String accessToken;

        public Response(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}

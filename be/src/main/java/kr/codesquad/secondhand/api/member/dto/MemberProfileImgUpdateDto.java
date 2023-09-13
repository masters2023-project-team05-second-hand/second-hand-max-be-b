package kr.codesquad.secondhand.api.member.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class MemberProfileImgUpdateDto {

    @Getter
    public static class Request {

        private final MultipartFile newProfileImg;

        public Request(MultipartFile newProfileImg) {
            this.newProfileImg = newProfileImg;
        }
    }

    @Getter
    public static class Response {

        private final String updatedImgUrl;

        public Response(String updatedImgUrl) {
            this.updatedImgUrl = updatedImgUrl;
        }
    }
}

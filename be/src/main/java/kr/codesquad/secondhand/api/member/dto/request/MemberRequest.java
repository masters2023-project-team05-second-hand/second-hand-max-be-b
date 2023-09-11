package kr.codesquad.secondhand.api.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class MemberRequest {

    @Getter
    public static class MemberProfileImgUpdateRequest {

        private MultipartFile newProfileImage;

        public MemberProfileImgUpdateRequest(MultipartFile newProfileImage) {
            this.newProfileImage = newProfileImage;
        }
    }

    @Getter
    @NoArgsConstructor()
    public static class MemberNicknameUpdateRequest {

        @NotNull
        private String newNickname;

        public MemberNicknameUpdateRequest(@JsonProperty("newNickname") String newNickname) {
            this.newNickname = newNickname;
        }
    }
}

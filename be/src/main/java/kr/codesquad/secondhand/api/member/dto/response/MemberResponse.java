package kr.codesquad.secondhand.api.member.dto.response;

import lombok.Getter;

public class MemberResponse {

    @Getter
    public static class MemberProfileImgUpdateResponse {

        private String updatedImgUrl;

        public MemberProfileImgUpdateResponse(String updatedImgUrl) {
            this.updatedImgUrl = updatedImgUrl;
        }
    }

    @Getter
    public static class MemberNicknameResponse {

        private String updatedNickname;

        public MemberNicknameResponse(String updatedNickname) {
            this.updatedNickname = updatedNickname;
        }
    }
}

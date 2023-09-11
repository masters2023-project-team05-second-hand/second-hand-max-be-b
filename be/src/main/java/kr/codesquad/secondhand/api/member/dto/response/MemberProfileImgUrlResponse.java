package kr.codesquad.secondhand.api.member.dto.response;

import lombok.Getter;

@Getter
public class MemberProfileImgUrlResponse {

    private String updatedImgUrl;

    public MemberProfileImgUrlResponse(String updatedImgUrl) {
        this.updatedImgUrl = updatedImgUrl;
    }
}

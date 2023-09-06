package kr.codesquad.secondhand.api.member.dto.response;

import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberProfileResponse {

    private final String nickName;
    private final String profileImgUrl;

    public MemberProfileResponse(String nickName, String profileImgUrl) {
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
    }

    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getNickname(), member.getProfileImgUrl());
    }
}

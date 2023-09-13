package kr.codesquad.secondhand.api.member.dto.response;

import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberProfileResponse {

    private final Long id;
    private final String nickname;
    private final String profileImgUrl;

    public MemberProfileResponse(Long id, String nickname, String profileImgUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }


    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getId(), member.getNickname(), member.getProfileImgUrl());
    }
}

package kr.codesquad.secondhand.api.member.dto.response;

import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileResponse {

    private Long id;
    private String nickname;
    private String profileImgUrl;

    public MemberProfileResponse(Long id, String nickname, String profileImgUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getId(), member.getNickname(), member.getProfileImgUrl());
    }
}

package kr.codesquad.secondhand.api.member.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.jwt.domain.Jwt;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import lombok.Getter;

@Getter
public class OAuthSignInResponse {

    private final Jwt tokens;
    private final List<MemberAddressResponse> addresses;
    private final MemberSignInResponse member;

    public OAuthSignInResponse(Jwt tokens, List<MemberAddressResponse> memberAddresses,
                               MemberSignInResponse memberSignInResponse) {
        this.tokens = tokens;
        this.addresses = memberAddresses;
        this.member = memberSignInResponse;
    }

    public static OAuthSignInResponse of(Jwt jwt, List<MemberAddress> memberAddress, Member member) {
        List<MemberAddressResponse> memberAddressResponses =
                memberAddress.stream()
                        .map(MemberAddressResponse::from)
                        .collect(Collectors.toUnmodifiableList());
        MemberSignInResponse memberSignInResponse = MemberSignInResponse.from(member);
        return new OAuthSignInResponse(jwt, memberAddressResponses, memberSignInResponse);
    }

    @Getter
    public static class MemberAddressResponse {

        private final Long id;
        private final String name;
        private final boolean isLastVisited;

        public MemberAddressResponse(Long id, String name, boolean isLastVisited) {
            this.id = id;
            this.name = name;
            this.isLastVisited = isLastVisited;
        }

        public static MemberAddressResponse from(MemberAddress memberAddress) {
            Address address = memberAddress.getAddress();
            return new MemberAddressResponse(address.getId(), address.getName(), memberAddress.isLastVisited());
        }
    }

    @Getter
    public static class MemberSignInResponse {

        private final String nickName;
        private final String profileImgUrl;

        public MemberSignInResponse(String nickName, String profileImgUrl) {
            this.nickName = nickName;
            this.profileImgUrl = profileImgUrl;
        }

        public static MemberSignInResponse from(Member member) {
            return new MemberSignInResponse(member.getNickname(), member.getProfileImgUrl());
        }
    }
}

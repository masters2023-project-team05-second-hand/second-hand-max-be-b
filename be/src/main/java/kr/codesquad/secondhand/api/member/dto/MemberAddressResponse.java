package kr.codesquad.secondhand.api.member.dto;

import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import lombok.Getter;

@Getter
public class MemberAddressResponse {

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
        return new MemberAddressResponse(address.getId(), address.getName(),
                memberAddress.isLastVisited());
    }
}

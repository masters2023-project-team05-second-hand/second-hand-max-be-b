package kr.codesquad.secondhand.api.member.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import lombok.Getter;

@Getter
public class MemberAddressResponse {

    private final Long id;
    private final String name;

    public MemberAddressResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<MemberAddressResponse> from(List<MemberAddress> memberAddresses) {
        return memberAddresses.stream()
                .map(memberAddress -> {
                    Address address = memberAddress.getAddress();
                    return new MemberAddressResponse(address.getId(), address.getName());
                })
                .collect(Collectors.toUnmodifiableList());
    }
}

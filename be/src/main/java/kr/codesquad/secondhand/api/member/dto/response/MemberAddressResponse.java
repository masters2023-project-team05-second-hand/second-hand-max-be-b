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
    private final boolean isLastVisited;

    public MemberAddressResponse(Long id, String name, boolean isLastVisited) {
        this.id = id;
        this.name = name;
        this.isLastVisited = isLastVisited;
    }

    public static List<MemberAddressResponse> from(List<MemberAddress> memberAddresses) {
        return memberAddresses.stream()
                .map(memberAddress -> {
                    Address address = memberAddress.getAddress();
                    return new MemberAddressResponse(address.getId(), address.getName(), memberAddress.isLastVisited());
                })
                .collect(Collectors.toUnmodifiableList());
    }
}

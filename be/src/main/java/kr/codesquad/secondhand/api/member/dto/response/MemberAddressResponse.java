package kr.codesquad.secondhand.api.member.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.member.domain.MemberAddress;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressResponse {

    private Long id;
    private String name;

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

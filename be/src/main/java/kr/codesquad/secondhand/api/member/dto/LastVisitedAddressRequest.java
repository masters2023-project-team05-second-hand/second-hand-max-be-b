package kr.codesquad.secondhand.api.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LastVisitedAddressRequest {

    private Long lastVisitedAddressId;

    public LastVisitedAddressRequest(Long lastVisitedAddressId) {
        this.lastVisitedAddressId = lastVisitedAddressId;
    }
}

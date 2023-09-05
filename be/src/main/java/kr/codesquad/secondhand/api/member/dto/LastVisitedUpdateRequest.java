package kr.codesquad.secondhand.api.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LastVisitedUpdateRequest {

    private Long lastVisitedAddressId;

    public LastVisitedUpdateRequest(Long lastVisitedAddressId) {
        this.lastVisitedAddressId = lastVisitedAddressId;
    }
}

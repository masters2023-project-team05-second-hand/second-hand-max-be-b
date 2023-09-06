package kr.codesquad.secondhand.api.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LastVisitedUpdateRequest {

    private Long lastVisitedAddressId;

}

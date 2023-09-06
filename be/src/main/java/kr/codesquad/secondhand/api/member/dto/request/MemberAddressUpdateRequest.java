package kr.codesquad.secondhand.api.member.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressUpdateRequest {

    private List<Long> addressIds;

}

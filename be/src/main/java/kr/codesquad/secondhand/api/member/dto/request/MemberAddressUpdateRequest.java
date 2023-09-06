package kr.codesquad.secondhand.api.member.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberAddressUpdateRequest {

    private List<Long> addressIds;

}

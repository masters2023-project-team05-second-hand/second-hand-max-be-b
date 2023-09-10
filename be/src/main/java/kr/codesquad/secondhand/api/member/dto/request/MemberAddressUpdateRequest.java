package kr.codesquad.secondhand.api.member.dto.request;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberAddressUpdateRequest {

    @Size(min = 1, max = 2, message = "동네 정보는 최소 1개, 최대 2개로 설정해야 합니다.")
    private List<Long> addressIds;

}

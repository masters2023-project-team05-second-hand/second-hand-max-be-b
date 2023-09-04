package kr.codesquad.secondhand.api.member.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressModifyRequest {

    private List<Long> addressIds;

    public MemberAddressModifyRequest(List<Long> addressIds) {
        this.addressIds = addressIds;
    }
}

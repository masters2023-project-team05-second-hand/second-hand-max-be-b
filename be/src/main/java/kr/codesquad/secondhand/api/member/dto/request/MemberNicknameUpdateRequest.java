package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberNicknameUpdateRequest {

    @NotNull(message = "수정할 닉네임이 존재하지 않습니다.")
    private String newNickname;

}

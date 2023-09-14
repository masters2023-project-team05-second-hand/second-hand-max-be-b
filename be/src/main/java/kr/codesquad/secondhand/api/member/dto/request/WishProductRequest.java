package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WishProductRequest {

    @NotNull(message = "productId가 Null입니다.")
    private Long productId;
    private Boolean isWished;
}

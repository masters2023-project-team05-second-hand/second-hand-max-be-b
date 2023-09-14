package kr.codesquad.secondhand.api.member.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class WishProductRequest {

    @NotEmpty(message = "productId가 비어있습니다.")
    private Long productId;
    private Boolean isWished;
}

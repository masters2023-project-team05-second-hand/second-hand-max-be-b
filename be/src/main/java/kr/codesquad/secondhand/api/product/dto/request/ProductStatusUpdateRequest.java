package kr.codesquad.secondhand.api.product.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductStatusUpdateRequest {

    @NotNull(message = "업데이트할 상품 상태 정보가 없습니다.")
    private Integer statusId;

}

package kr.codesquad.secondhand.api.product.dto;

import lombok.Getter;

@Getter
public class ProductCreateResponse {

    private final Long productId;

    public ProductCreateResponse(Long productId) {
        this.productId = productId;
    }
}

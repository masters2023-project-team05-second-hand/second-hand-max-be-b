package kr.codesquad.secondhand.api.member.dto.response;

import lombok.Getter;

@Getter
public class ProductWishStatusResponse {

    private Boolean isWished;

    public ProductWishStatusResponse(Boolean isWished) {
        this.isWished = isWished;
    }
}

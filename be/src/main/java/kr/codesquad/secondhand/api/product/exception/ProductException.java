package kr.codesquad.secondhand.api.product.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class ProductException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "상품 관련 에러입니다.";

    public ProductException() {
        super(ERROR_MESSAGE);
    }

    public ProductException(String message) {
        super(message);
    }
}

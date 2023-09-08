package kr.codesquad.secondhand.api.product.exception;

public class ProductImageException extends ProductException {

    private static final String ERROR_MESSAGE = "상품 이미지 관련 에러입니다.";

    public ProductImageException() {
        super(ERROR_MESSAGE);
    }

    public ProductImageException(String message) {
        super(message);
    }
}

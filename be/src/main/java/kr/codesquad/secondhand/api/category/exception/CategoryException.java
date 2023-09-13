package kr.codesquad.secondhand.api.category.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class CategoryException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "카테고리 관련 오류입니다.";

    public CategoryException() {
        super(ERROR_MESSAGE);
    }

    public CategoryException(String message) {
        super(message);
    }
}

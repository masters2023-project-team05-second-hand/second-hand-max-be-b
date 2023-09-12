package kr.codesquad.secondhand.api.category.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class CategoryException extends CustomRuntimeException {

    public CategoryException(String message) {
        super(message);
    }
}

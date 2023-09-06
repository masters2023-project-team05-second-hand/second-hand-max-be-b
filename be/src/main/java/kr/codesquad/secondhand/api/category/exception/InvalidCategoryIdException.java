package kr.codesquad.secondhand.api.category.exception;

public class InvalidCategoryIdException extends CategoryException {

    private static final String ERROR_MESSAGE = "존재하지 않는 카테고리 Id 입니다.";

    public InvalidCategoryIdException() {
        super(ERROR_MESSAGE);
    }
}

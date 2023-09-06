package kr.codesquad.secondhand.global.exception;

import kr.codesquad.secondhand.api.category.exception.CategoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseEntity handleException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity("서버 오류입니다.");
    }

    @ExceptionHandler(WebClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleWebClientException(WebClientException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity("Oauth 서버 관련 오류입니다.");
    }

    @ExceptionHandler(CategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleCategoryException(CategoryException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

}

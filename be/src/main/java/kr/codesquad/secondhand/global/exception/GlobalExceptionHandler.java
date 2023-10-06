package kr.codesquad.secondhand.global.exception;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.exception.CategoryException;
import kr.codesquad.secondhand.api.chat.exception.ChatRoomException;
import kr.codesquad.secondhand.api.member.exception.MemberException;
import kr.codesquad.secondhand.api.oauth.exception.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleBindException(BindException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(getFieldErrorMessages(e.getBindingResult()));
    }

    private List<String> getFieldErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleCustomException(CustomException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(CustomRuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleCustomRuntimeException(CustomRuntimeException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(MemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleMemberException(MemberException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(OAuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleOauthException(OAuthException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(CategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleCategoryException(CategoryException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ChatRoomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleChatRoomException(ChatRoomException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseEntity(e.getMessage());
    }
}

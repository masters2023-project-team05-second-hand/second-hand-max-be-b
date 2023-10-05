package kr.codesquad.secondhand.api.chat.exception;

import kr.codesquad.secondhand.global.exception.CustomRuntimeException;

public class ChatRoomException extends CustomRuntimeException {

    private static final String ERROR_MESSAGE = "채팅방 관련 오류입니다.";

    public ChatRoomException() {
        super(ERROR_MESSAGE);
    }

    public ChatRoomException(String message) {
        super(message);
    }
}

package kr.codesquad.secondhand.api.chat.exception;

public class ChatRoomExistsException extends ChatRoomException {

    private static final String ERROR_MESSAGE = "이미 존재하는 채팅방입니다.";

    public ChatRoomExistsException() {
        super(ERROR_MESSAGE);
    }
}

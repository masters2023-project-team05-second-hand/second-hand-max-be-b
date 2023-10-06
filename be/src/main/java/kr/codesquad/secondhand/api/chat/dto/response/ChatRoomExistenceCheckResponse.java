package kr.codesquad.secondhand.api.chat.dto.response;

import lombok.Getter;

@Getter
public class ChatRoomExistenceCheckResponse {

    private final String roomId;

    public ChatRoomExistenceCheckResponse(String roomId) {
        this.roomId = roomId;
    }
}

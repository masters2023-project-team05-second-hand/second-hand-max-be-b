package kr.codesquad.secondhand.api.chat.dto.reponse;

import lombok.Getter;

@Getter
public class ChatRoomExistenceCheckResponse {

    private final String roomId;

    public ChatRoomExistenceCheckResponse(String roomId) {
        this.roomId = roomId;
    }
}

package kr.codesquad.secondhand.api.chat.dto;

import java.time.Instant;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.Getter;

public class ChatMessagePayload {

    @Getter
    public static class Request {

        private String roomId;
        private Long senderId;
        private String message;

        public ChatMessage toEntity(Member sender) {
            return new ChatMessage(roomId, sender, message);
        }
    }

    @Getter
    public static class Response {

        private String roomId;
        private Long senderId;
        private String message;
        private Instant sentTime;

        public Response(String roomId, Long senderId, String message, Instant sentTime) {
            this.roomId = roomId;
            this.senderId = senderId;
            this.message = message;
            this.sentTime = sentTime;
        }

        public static Response from(ChatMessage chatMessage) {
            return new Response(
                    chatMessage.getRoomId(),
                    chatMessage.getSender().getId(),
                    chatMessage.getMessage(),
                    chatMessage.getSentTime()
            );
        }
    }
}

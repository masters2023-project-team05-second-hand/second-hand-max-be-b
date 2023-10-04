package kr.codesquad.secondhand.api.chat.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import lombok.Getter;

@Getter
public class ChatRoomMessagesReadResponse {

    private final Long senderId;
    private final String content;
    private final Instant sentTime;

    private ChatRoomMessagesReadResponse(Long senderId, String content, Instant sentTime) {
        this.senderId = senderId;
        this.content = content;
        this.sentTime = sentTime;
    }

    private static ChatRoomMessagesReadResponse from(ChatMessage chatMessage) {
        return new ChatRoomMessagesReadResponse(
                chatMessage.getSender().getId(),
                chatMessage.getMessage(),
                chatMessage.getSentTime()
        );
    }

    public static List<ChatRoomMessagesReadResponse> from(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(ChatRoomMessagesReadResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}

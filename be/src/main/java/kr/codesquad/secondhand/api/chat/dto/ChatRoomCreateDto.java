package kr.codesquad.secondhand.api.chat.dto;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class ChatRoomCreateDto {

    @Getter
    public static class Request {

        @NotNull(message = "생성할 채팅방의 상품 정보가 비어있습니다.")
        private Long productId;

        @NotBlank(message = "생성할 채팅방의 메시지 내용이 없습니다.")
        private String message;
    }

    @Getter
    public static class Response {

        private final String roomId;
        private final Instant sentTime;

        public Response(String roomId, Instant sentTime) {
            this.roomId = roomId;
            this.sentTime = sentTime;
        }
    }
}

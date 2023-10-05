package kr.codesquad.secondhand.api.chat.controller;

import kr.codesquad.secondhand.api.chat.dto.ChatMessagePayload;
import kr.codesquad.secondhand.api.chat.service.ChatMessageFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageFacadeService chatMessageFacadeService;

    @SneakyThrows
    @MessageMapping("/chat/message/{roomId}")
    public void sendMessage(@Payload ChatMessagePayload.Request request, @DestinationVariable String roomId) {
        chatMessageFacadeService.sendMessage(request, roomId);
    }
}

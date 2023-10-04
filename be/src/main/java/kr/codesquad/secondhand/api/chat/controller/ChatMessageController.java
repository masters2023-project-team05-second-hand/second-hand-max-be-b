package kr.codesquad.secondhand.api.chat.controller;

import kr.codesquad.secondhand.api.chat.dto.ChatMessagePayload;
import kr.codesquad.secondhand.api.chat.service.ChatMessageFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageFacadeService chatMessageFacadeService;

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessagePayload.Request request) {
        chatMessageFacadeService.sendMessage(request);
    }
}

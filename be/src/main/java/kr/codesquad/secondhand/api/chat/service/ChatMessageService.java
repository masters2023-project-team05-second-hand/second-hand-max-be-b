package kr.codesquad.secondhand.api.chat.service;

import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.chat.dto.ChatMessagePayload;
import kr.codesquad.secondhand.api.chat.repository.ChatMessageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final SimpMessageSendingOperations template;
    private final ChatMessageRepositoryImpl chatMessageRepository;

    public void sendMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
        template.convertAndSend(
                "/sub/chat/room/" + chatMessage.getRoomId(),
                ChatMessagePayload.Response.from(chatMessage)
        );
    }
}

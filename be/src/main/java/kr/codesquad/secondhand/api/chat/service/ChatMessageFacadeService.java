package kr.codesquad.secondhand.api.chat.service;

import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.chat.dto.ChatMessagePayload;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageFacadeService {

    private final ChatMessageService chatMessageService;
    private final ChatService chatService;
    private final MemberService memberService;

    @Transactional
    public void sendMessage(ChatMessagePayload.Request request) {
        Member sender = memberService.getMemberReferenceById(request.getSenderId()); // TODO 헤더 토큰 파싱하는 것으로 개선 필요
        ChatMessage chatMessage = request.toEntity(sender);

        chatService.updateLastMessage(chatMessage.getRoomId(), chatMessage);
        chatMessageService.sendMessage(chatMessage);
    }
}

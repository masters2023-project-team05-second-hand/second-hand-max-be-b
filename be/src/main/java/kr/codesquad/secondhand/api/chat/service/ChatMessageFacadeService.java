package kr.codesquad.secondhand.api.chat.service;

import io.jsonwebtoken.Claims;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.chat.dto.ChatMessagePayload;
import kr.codesquad.secondhand.api.jwt.exception.TokenNotFoundException;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.global.util.HttpAuthorizationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageFacadeService {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Transactional
    public void sendMessage(ChatMessagePayload.Request request, String roomId) throws TokenNotFoundException {

        String token = HttpAuthorizationUtils.extractAccessToken(request.getAuthorization());
        Claims claims = jwtProvider.getClaims(token);
        Long memberId = getMemberId(claims);
        Member sender = memberService.getMemberReferenceById(memberId); // TODO 헤더 토큰 파싱하는 것으로 개선 필요

        ChatMessage chatMessage = request.toEntity(roomId, sender);
        chatRoomService.updateLastMessage(chatMessage.getRoomId(), chatMessage);

        chatMessageService.sendMessage(chatMessage);
    }

    private Long getMemberId(Claims claims){
        return Long.valueOf(claims.get("memberId").toString());
    }
}

package kr.codesquad.secondhand.api.chat.service;

import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.chat.dto.ChatRoomCreateDto;
import kr.codesquad.secondhand.api.chat.dto.reponse.ChatRoomExistenceCheckResponse;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatFacadeService {

    private final ChatService chatService;
    private final ProductService productService;
    private final MemberService memberService;

    public ChatRoomCreateDto.Response createChatRoom(Long memberId, Long productId) {
        Member member = memberService.getMemberReferenceById(memberId);
        Product product = productService.findById(productId);
        ChatRoom chatRoom = chatService.createChatRoom(product, member);

        // TODO request의 첫 메시지 관련 처리 필요
        return new ChatRoomCreateDto.Response(chatRoom.getId());
    }

    public ChatRoomExistenceCheckResponse checkChatRoomExistence(Long memberId, Long productId) {
        return chatService.findChatRoomByMemberIdAndProductId(memberId, productId)
                .map(chatRoom -> new ChatRoomExistenceCheckResponse(chatRoom.getId()))
                .orElse(new ChatRoomExistenceCheckResponse(null));
    }
}

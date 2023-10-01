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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatFacadeService {

    private final ChatService chatService;
    private final ProductService productService;
    private final MemberService memberService;

    @Transactional
    public ChatRoomCreateDto.Response createChatRoom(Long memberId, ChatRoomCreateDto.Request request) {
        Member member = memberService.getMemberReferenceById(memberId);
        Product product = productService.findById(request.getProductId());
        ChatRoom chatRoom = chatService.createChatRoom(product, member, request.getMessage().getContent());

        return new ChatRoomCreateDto.Response(chatRoom.getRoomId());
    }

    public ChatRoomExistenceCheckResponse checkChatRoomExistence(Long memberId, Long productId) {
        return chatService.findChatRoomByMemberIdAndProductId(memberId, productId)
                .map(chatRoom -> new ChatRoomExistenceCheckResponse(chatRoom.getRoomId()))
                .orElse(new ChatRoomExistenceCheckResponse(null));
    }
}

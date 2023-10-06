package kr.codesquad.secondhand.api.chat.service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.chat.dto.ChatRoomCreateDto;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomExistenceCheckResponse;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomMessagesReadResponse;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomReadResponse;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomFacadeService {

    private final ChatRoomService chatRoomService;
    private final ProductService productService;
    private final MemberService memberService;

    @Transactional
    public ChatRoomCreateDto.Response createChatRoom(Long memberId, ChatRoomCreateDto.Request request) {
        Member member = memberService.getMemberReferenceById(memberId);
        Product product = productService.findById(request.getProductId());
        ChatRoom chatRoom = chatRoomService.createChatRoom(product, member, request.getMessage());
        Instant sentTime = chatRoom.getLastMessage().getSentTime();
        return new ChatRoomCreateDto.Response(chatRoom.getRoomId(), sentTime);
    }

    public ChatRoomExistenceCheckResponse checkChatRoomExistence(Long memberId, Long productId) {
        return chatRoomService.findChatRoomByMemberIdAndProductId(memberId, productId)
                .map(chatRoom -> new ChatRoomExistenceCheckResponse(chatRoom.getRoomId()))
                .orElse(new ChatRoomExistenceCheckResponse(null));
    }

    @Transactional
    public List<ChatRoomReadResponse> findAllChatRoomsBy(Long memberId, Long productId) {
        Member loginMember = memberService.getMemberReferenceById(memberId);
        Optional<List<ChatRoom>> chatRooms = chatRoomService.findAllChatRoomsBy(memberId, productId);

        if (chatRooms.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Member> otherMembers = chatRoomService.findOtherMembers(chatRooms.get(), loginMember);
        return ChatRoomReadResponse.from(chatRooms.get(), otherMembers);
    }

    public List<ChatRoomMessagesReadResponse> findChatRoomMessagesBy(String roomId) {
        List<ChatMessage> messages = chatRoomService.findChatMessagesBy(roomId);
        return ChatRoomMessagesReadResponse.from(messages);
    }

    @Transactional
    public void deleteChatRoomBy(String roomId) {
        chatRoomService.deleteChatRoomBy(roomId);
        // TODO 채팅방 나갈 때 메세지도 삭제 필요(2명 다 갔을 때만?, 정책 논의 필요)
    }
}

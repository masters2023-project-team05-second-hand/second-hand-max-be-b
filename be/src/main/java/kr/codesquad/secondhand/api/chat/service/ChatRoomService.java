package kr.codesquad.secondhand.api.chat.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.chat.exception.ChatRoomExistsException;
import kr.codesquad.secondhand.api.chat.repository.ChatMessageRepositoryImpl;
import kr.codesquad.secondhand.api.chat.repository.ChatRoomRepositoryImpl;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepositoryImpl chatRoomRepository;
    private final ChatMessageRepositoryImpl chatMessageRepository; // TODO chatmessage 관련 로직 ChatMessageService로 이동 필요

    public ChatRoom createChatRoom(Product product, Member member, String message) {
        if (chatRoomRepository.existsByBuyerIdAndProductId(member.getId(), product.getId())) {
            throw new ChatRoomExistsException();
        }

        ChatRoom chatRoom = saveChatRoom(product, member);
        ChatMessage chatMessage = saveChatMessage(message, member, chatRoom.getRoomId());
        updateLastMessage(chatRoom.getRoomId(), chatMessage);

        return chatRoom;
    }

    private ChatRoom saveChatRoom(Product product, Member member) {
        ChatRoom chatRoom = ChatRoom.create(product, member);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    private ChatMessage saveChatMessage(String message, Member sender, String roomId) {
        ChatMessage chatMessage = new ChatMessage(roomId, sender, message);
        return chatMessageRepository.save(chatMessage);
    }

    public void updateLastMessage(String roomId, ChatMessage chatMessage) {
        // TODO save 후 바로 업데이트가 되지 않아서 save -> find -> update 하고 있는데 쿼리 개선 필요
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        chatRoom.updateLastMessage(chatMessage);
    }

    public int countChatRoomsBy(Long productId) {
        return chatRoomRepository.countChatRoomsByProductId(productId);
    }

    public Optional<ChatRoom> findChatRoomByMemberIdAndProductId(Long memberId, Long productId) {
        return chatRoomRepository.findByBuyerIdAndProductId(memberId, productId);
    }

    public Optional<List<ChatRoom>> findAllChatRoomsBy(Long memberId, Long productId) {
        return chatRoomRepository.findChatRoomsBy(memberId, productId);
    }

    /**
     * @return RoomId에 매핑된 otherMember
     */
    public Map<String, Member> findOtherMembers(List<ChatRoom> chatRooms, Member loginMember) {
        return chatRooms.stream()
                .collect(Collectors.toUnmodifiableMap(
                        ChatRoom::getRoomId,
                        chatRoom -> {
                            if (chatRoom.getProduct().isSeller(loginMember)) {
                                return chatRoom.getBuyer();
                            }
                            return chatRoom.getProduct().getSeller();
                        }
                ));
    }

    public List<ChatMessage> findChatMessagesBy(String roomId) {
        return chatMessageRepository.findAllByRoomId(roomId);
    }

    public void deleteChatRoomBy(String roomId) {
        chatRoomRepository.deleteByRoomId(roomId);
    }
}

package kr.codesquad.secondhand.api.chat.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.chat.repository.ChatRoomRepositoryImpl;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepositoryImpl chatRoomRepository;

    public ChatRoom createChatRoom(Product product, Member member) {
        // TODO 채팅방이 이미 존재할 경우, 예외 처리 필요
        ChatRoom chatRoom = ChatRoom.create(product, member);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public Optional<ChatRoom> findChatRoomByMemberIdAndProductId(Long memberId, Long productId) {
        return chatRoomRepository.findByMemberIdAndProductId(memberId, productId);
    }
}

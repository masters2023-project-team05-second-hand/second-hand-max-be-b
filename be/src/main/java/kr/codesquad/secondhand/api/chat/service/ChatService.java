package kr.codesquad.secondhand.api.chat.service;

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
        ChatRoom chatRoom = ChatRoom.create(product, member);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

}

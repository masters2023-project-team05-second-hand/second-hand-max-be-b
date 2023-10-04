package kr.codesquad.secondhand.api.chat.repository;

import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepositoryImpl extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByBuyerIdAndProductId(Long buyerId, Long productId);

    ChatRoom findByRoomId(String roomId);

    Optional<List<ChatRoom>> findByProductSellerOrBuyer(Member seller, Member buyer);

    void deleteByRoomId(String roomId);
}

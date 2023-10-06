package kr.codesquad.secondhand.api.chat.repository;

import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepositoryImpl extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByBuyerIdAndProductId(Long buyerId, Long productId);

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);

    ChatRoom findByRoomId(String roomId);

    /**
     * @param productId 0은 전체 목록을 의미
     */
    @Query("SELECT chatRoom FROM ChatRoom chatRoom "
            + "WHERE (:productId = 0L OR chatRoom.product.id = :productId) "
            + "AND (chatRoom.product.seller.id = :memberId OR chatRoom.buyer.id = :memberId)")
    Optional<List<ChatRoom>> findChatRoomsBy(@Param("memberId") Long memberId, @Param("productId") Long productId);

    int countChatRoomsByProductId(Long productId);

    void deleteByRoomId(String roomId);
}

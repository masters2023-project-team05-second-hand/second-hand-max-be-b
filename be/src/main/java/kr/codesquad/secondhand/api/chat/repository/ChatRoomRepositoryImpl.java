package kr.codesquad.secondhand.api.chat.repository;

import java.util.Optional;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepositoryImpl extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByMemberIdAndProductId(Long member_id, Long product_id);

}

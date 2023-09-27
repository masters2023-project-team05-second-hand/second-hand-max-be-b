package kr.codesquad.secondhand.api.chat.repository;

import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepositoryImpl extends JpaRepository<ChatRoom, String> {
}

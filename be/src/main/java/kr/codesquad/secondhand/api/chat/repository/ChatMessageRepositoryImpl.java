package kr.codesquad.secondhand.api.chat.repository;

import java.util.List;
import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepositoryImpl extends JpaRepository<ChatMessage, String> {

    // TODO 슬라이싱 하면 sentTime 기준 DESC로 정렬 필요
    List<ChatMessage> findAllByRoomId(String roomId);

}

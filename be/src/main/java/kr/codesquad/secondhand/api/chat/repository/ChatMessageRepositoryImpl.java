package kr.codesquad.secondhand.api.chat.repository;

import kr.codesquad.secondhand.api.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepositoryImpl extends JpaRepository<ChatMessage, String> {
}

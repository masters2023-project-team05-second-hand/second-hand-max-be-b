package kr.codesquad.secondhand.api.chat.domain;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @OneToOne
    @JoinColumn(name = "last_message_id")
    private ChatMessage lastMessage;

    @CreationTimestamp
    private Instant createdTime;

    private ChatRoom(String roomId, Product product, Member buyer) {
        this.roomId = roomId;
        this.product = product;
        this.buyer = buyer;
    }

    public static ChatRoom create(Product product, Member member) {
        String id = UUID.randomUUID().toString();
        return new ChatRoom(id, product, member);
    }

    public void updateLastMessage(ChatMessage chatMessage) {
        this.lastMessage = chatMessage;
    }
}

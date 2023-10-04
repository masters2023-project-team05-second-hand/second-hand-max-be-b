package kr.codesquad.secondhand.api.chat.dto.reponse;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.chat.domain.ChatRoom;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.dto.response.MemberProfileResponse;
import kr.codesquad.secondhand.api.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomReadResponse {

    private final ChatRoomDto chatRoom;
    private final ProductDto product;

    private ChatRoomReadResponse(ChatRoom chatRoom, Member otherMember) {
        this.chatRoom = ChatRoomDto.from(chatRoom, otherMember);
        this.product = ProductDto.from(chatRoom.getProduct());
    }

    public static List<ChatRoomReadResponse> from(List<ChatRoom> chatRooms, Map<String, Member> otherMembers) {
        return chatRooms.stream()
                .map(chatRoom -> {
                    Member otherMember = otherMembers.get(chatRoom.getRoomId());
                    return new ChatRoomReadResponse(chatRoom, otherMember);
                })
                .collect(Collectors.toList());
    }

    @Getter
    private static class ChatRoomDto {

        private final MemberProfileResponse otherMember;
        private final MessageDto message;
        // TODO 안 읽은 메시지 개수

        private ChatRoomDto(MemberProfileResponse otherMember, MessageDto message) {
            this.otherMember = otherMember;
            this.message = message;
        }

        private static ChatRoomDto from(ChatRoom chatRoom, Member otherMember) {
            return new ChatRoomDto(
                    MemberProfileResponse.from(otherMember),
                    MessageDto.from(chatRoom)
            );
        }
    }

    @Getter
    private static class MessageDto {

        private final String lastMessage;
        private final Instant lastSentTime;

        private MessageDto(String lastMessage, Instant lastSentTime) {
            this.lastMessage = lastMessage;
            this.lastSentTime = lastSentTime;
        }

        private static MessageDto from(ChatRoom chatRoom) {
            return new MessageDto(
                    chatRoom.getLastMessage().getMessage(),
                    chatRoom.getLastMessage().getSentTime()
            );
        }
    }

    @Getter
    private static class ProductDto {

        private final Long productId;
        private final String title;
        private final URL thumbnailUrl;
        private final Long price;
        private final Integer status;

        @Builder
        private ProductDto(Long productId, String title, URL thumbnailUrl, Long price, Integer status) {
            this.productId = productId;
            this.title = title;
            this.thumbnailUrl = thumbnailUrl;
            this.price = price;
            this.status = status;
        }

        private static ProductDto from(Product product) {
            return ProductDto.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .thumbnailUrl(product.getThumbnailImgUrl())
                    .price(product.getPrice())
                    .status(product.getStatusId())
                    .build();
        }
    }
}
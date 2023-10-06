import { ChatItem } from "@api/type";
import { ROUTE_PATH } from "@router/constants";
import { TextBold, TextDefault, TextWeak } from "@styles/common";
import { convertPastTimestamp } from "@utils/time";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

type ChatListItemProps = {
  chatItem: ChatItem;
};

export default function ChatListItem({ chatItem }: ChatListItemProps) {
  const navigate = useNavigate();
  const { roomId, otherMember, message, unreadMessageCount, product } =
    chatItem;

  const onClickItem = () => {
    navigate(`${ROUTE_PATH.chatting}/${roomId}`, {
      state: {
        product: product,
        partner: otherMember,
      },
    });
  };

  return (
    <StyledChatListItem onClick={onClickItem}>
      <UserImage src={otherMember.profileImgUrl} />
      <ChatInfo>
        <div className="sub-info">
          <TextBold>{otherMember.nickname}</TextBold>
          <TextWeak>{convertPastTimestamp(message.lastSentTime)}</TextWeak>
        </div>
        <TextDefault>{message.lastMessage}</TextDefault>
      </ChatInfo>
      {!!unreadMessageCount && <Badge>{unreadMessageCount}</Badge>}
      <Thumbnail src={product.thumbnailUrl} />
    </StyledChatListItem>
  );
}

const StyledChatListItem = styled.li`
  display: flex;
  padding: 16px;
  gap: 8px;
  border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};

  &:hover {
    cursor: pointer;
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }
`;

const UserImage = styled.img`
  width: 48px;
  height: 48px;
  border-radius: ${({ theme: { radius } }) => radius[50]};
`;

const ChatInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;

  .sub-info {
    display: flex;
    gap: 4px;
    align-items: center;
  }
`;

const Badge = styled.div`
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralBackground};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
  border-radius: ${({ theme: { radius } }) => radius[50]};
`;

const Thumbnail = styled.img`
  width: 48px;
  height: 48px;
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  border-radius: ${({ theme: { radius } }) => radius[8]};
`;

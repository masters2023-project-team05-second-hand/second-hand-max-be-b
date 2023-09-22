import { ChatItem } from "@pages/ChatList";
import { TextBold, TextDefault, TextWeak } from "@styles/common";
import { convertPastTimestamp } from "@utils/time";
import { styled } from "styled-components";

type ChatListItemProps = {
  onClick: (id: number) => void;
  chatItem: ChatItem;
};

export default function ChatListItem({ onClick, chatItem }: ChatListItemProps) {
  return (
    <StyledChatListItem onClick={() => onClick(chatItem.id)}>
      <UserImage src={chatItem.userImage} />
      <ChatInfo>
        <div className="sub-info">
          <TextBold>{chatItem.userName}</TextBold>
          <TextWeak>{convertPastTimestamp(chatItem.createdTime)}</TextWeak>
        </div>
        <TextDefault>{chatItem.lastMessage}</TextDefault>
      </ChatInfo>
      <Badge>{chatItem.unreadCount}</Badge>
      <Thumbnail src={chatItem.thumbnailImage} />
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

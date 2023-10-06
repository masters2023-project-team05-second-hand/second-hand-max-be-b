import { TextWeak } from "@styles/common";
import { formatKoreanDate } from "@utils/index";
import styled from "styled-components";
import ChatMessage from "./ChatMessage";

export type Chat = {
  id: number;
  content: string;
  sentTime: string;
  isMine: boolean;
};

export default function DailyChat({
  date,
  chats,
}: {
  date: string;
  chats: Chat[];
}) {
  return (
    <StyledDailyChat>
      <TextWeak className="daily-date">{formatKoreanDate(date)}</TextWeak>
      {chats.map((chat) => (
        // TODO: sentTime 사용해서 해당 시간 첫번째 bubble에만 시간 표시하기
        <ChatMessage
          key={chat.id}
          content={chat.content}
          isMine={chat.isMine}
        />
      ))}
    </StyledDailyChat>
  );
}

const StyledDailyChat = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 0.5rem 1rem;

  .daily-date {
    align-self: center;
  }
`;

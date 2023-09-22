import { TextWeak } from "@styles/common";
import { formatKoreanDate } from "@utils/index";
import styled from "styled-components";
import ChatBubble from "./ChatBubble";

export default function DailyChat({
  date,
  chats,
}: {
  date: string;
  chats: {
    id: number;
    content: string;
    createdAt: string;
    isMine: boolean;
  }[];
}) {
  return (
    <StyledDailyChat>
      <TextWeak className="daily-date">{formatKoreanDate(date)}</TextWeak>
      {chats.map((chat) => (
        // TODO: createdAt 사용해서 해당 시간 첫번째 bubble에만 시간 표시하기
        <ChatBubble key={chat.id} content={chat.content} isMine={chat.isMine} />
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

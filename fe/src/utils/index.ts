import { Chat } from "@components/ChatRoom/DailyChat";

export const isSameItems = (a: number[], b: number[]) => {
  const setA = new Set(a);
  const setB = new Set(b);

  return setA.size === setB.size && [...setA].every((value) => setB.has(value));
};

export const getFormattedPrice = (inputValue: string) => {
  const onlyNumber = inputValue.replace(/[^\d]+/g, "");
  const limitedPrice = onlyNumber.slice(0, 9);

  if (limitedPrice) {
    const krw = parseInt(limitedPrice).toLocaleString("ko-KR");

    return krw;
  } else {
    return ""; // Notice: inputValue의 length가 0일 경우 NaN 방지
  }
};

export const formatKoreanDate = (date: string) => {
  return new Date(date).toLocaleDateString("ko-KR", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

export function delay(time: number) {
  return new Promise((resolve) => setTimeout(resolve, time));
}

export function getLastWord(string: string | undefined) {
  if (!string) {
    return "";
  }

  const splittedString = string.split(" ");
  const lastWord = splittedString[splittedString.length - 1];

  return lastWord;
}

export const checkTokenExpiration = () => {
  const expirationTime = localStorage.getItem("expirationTime");
  const isValidToken =
    !!expirationTime && Date.now() < parseInt(expirationTime, 10);

  return isValidToken;
};

// TODO: 백엔드에 response format 변경 요청 후 삭제 예정
export const groupChatsByDate = (messages: Chat[]) => {
  const groupedChats = new Map<string, Chat[]>();

  messages.forEach((chat: Chat) => {
    const dateKey = chat.sentTime.split("T")[0];

    if (!groupedChats.get(dateKey)) {
      groupedChats.set(dateKey, []);
    }

    groupedChats.get(dateKey)?.push(chat);
  });

  const result: { date: string; chats: Chat[] }[] = [];
  groupedChats.forEach((chats, date) => {
    result.push({
      date,
      chats,
    });
  });

  return result;
};

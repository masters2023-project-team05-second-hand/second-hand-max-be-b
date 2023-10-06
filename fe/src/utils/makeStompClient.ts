import { CHAT_API_PATH } from "@api/chat/constants";
import { Client } from "@stomp/stompjs";

const { VITE_STOMP_SERVER_URL } = import.meta.env;

export default function makeStompClient({
  roomId,
  onSubscribe,
}: {
  roomId: string;
  onSubscribe: (message: string) => void;
  onDisconnect?: () => void;
}) {
  const destination = `${CHAT_API_PATH.sub}/${roomId}`;
  const client = new Client({
    brokerURL: VITE_STOMP_SERVER_URL,
    connectHeaders: {
      "Authorization": `Bearer ${localStorage.getItem("accessToken")}`,
      "Content-Type": "application/json",
    },
    onConnect: () => {
      client.subscribe(
        destination,
        (message) => {
          onSubscribe(message.body);
        },
        {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        }
      );
    },
    onDisconnect: () => console.log("TODO: 채팅방 이탈 API 요청할 예정"),
  });

  return client;
}

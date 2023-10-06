import { CHAT_API_PATH } from "@api/chat/constants";
import { useGetChatDetailQuery, useMakeRoomMutation } from "@api/chat/queries";
import { ChatMessage, ChatRoomLocationState } from "@api/type";
import ChatBar from "@components/ChatRoom/ChatBar";
import ChatRoomTopBar from "@components/ChatRoom/ChatRoomTopBar";
import DailyChat, { Chat } from "@components/ChatRoom/DailyChat";
import ProductBanner from "@components/ChatRoom/ProductBanner";
import { useToast } from "@hooks/useToast";
import { Client } from "@stomp/stompjs";
import { BottomBar, Page, PageContent } from "@styles/common";
import { groupChatsByDate } from "@utils/index";
import makeStompClient from "@utils/makeStompClient";
import { useCallback, useEffect, useRef, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { useMemberValue } from "store";

export default function ChatRoom() {
  const { roomId } = useParams();
  const {
    state: { product, partner },
  }: { state: ChatRoomLocationState } = useLocation();
  const { id: memberId } = useMemberValue();
  const { toast } = useToast();

  const [currentRoomId, setCurrentRoomId] = useState(roomId);
  const [currentChats, setCurrentChats] = useState<Chat[]>([]);

  const {
    data: allChatList,
    isSuccess,
    isError,
  } = useGetChatDetailQuery(roomId ?? "");

  useEffect(() => {
    if (isSuccess) {
      setCurrentChats(
        allChatList.map((chat: ChatMessage, index) => {
          return {
            id: index,
            content: chat.content,
            sentTime: chat.sentTime,
            isMine: chat.senderId === memberId,
          };
        })
      );
    }
    if (isError) {
      toast({
        title: "이전 채팅 불러오기 실패",
        message:
          "이전 채팅 내용을 불러오는데 실패했습니다. 잠시 후 다시 시도해주세요.",
        type: "error",
      });
    }
  }, [allChatList, isSuccess, memberId, isError, toast]);

  const appendNewChat = useCallback(
    ({
      message,
      sentTime,
      isMine,
    }: {
      message: string;
      sentTime: string;
      isMine: boolean;
    }) =>
      setCurrentChats((prev) => [
        ...prev,
        {
          id: prev.length,
          content: message,
          sentTime,
          isMine,
        },
      ]),
    []
  );

  const { onPostNewChatRoom } = useMakeRoomMutation({
    productId: product.productId,
    callback: ({ roomId, message, sentTime }) => {
      setCurrentRoomId(roomId);
      appendNewChat({
        message,
        sentTime,
        isMine: true,
      });
    },
  });

  // TODO: custom hook으로 분리하기
  const client = useRef<Client | null>(null);
  const pubMessage = (message: string) => {
    client.current?.publish({
      destination: `${CHAT_API_PATH.pub}/${currentRoomId}`,
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("accessToken")}`,
      },
      // TODO: BE-B팀 헤더 파싱 문제로 임시로 Body에 추가해둔 Authorization 삭제하기
      body: JSON.stringify({
        message,
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      }),
    });
  };

  useEffect(() => {
    if (!currentRoomId) {
      return;
    }

    client.current = makeStompClient({
      roomId: currentRoomId,
      onSubscribe: (newChat) => {
        const { message, sentTime, senderId } = JSON.parse(newChat);
        appendNewChat({
          message,
          sentTime,
          isMine: senderId === memberId,
        });
      },
    });
    client.current.activate();

    return () => {
      client.current?.deactivate();
    };
  }, [currentRoomId, memberId, appendNewChat]);

  const chatList = groupChatsByDate(currentChats);
  const havePrevChats = !!chatList.length;
  const onMessageSubmit = havePrevChats ? pubMessage : onPostNewChatRoom;

  return (
    <Page>
      <ChatRoomTopBar partnerName={partner.nickname} roomId={currentRoomId} />
      <ProductBanner product={product} />
      <PageContent>
        {chatList.map((dailyChatList) => (
          <DailyChat
            key={dailyChatList.date}
            date={dailyChatList.date}
            chats={dailyChatList.chats}
          />
        ))}
      </PageContent>
      <BottomBar>
        <ChatBar onMessageSubmit={onMessageSubmit} />
      </BottomBar>
    </Page>
  );
}

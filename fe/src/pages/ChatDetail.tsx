import ChatBar from "@components/ChatDetail/ChatBar";
import ChatMoreButton from "@components/ChatDetail/ChatMoreButton";
import DailyChat from "@components/ChatDetail/DailyChat";
import ProductBanner from "@components/ChatDetail/ProductBanner";
import TopBar from "@components/TopBar";
import BackButton from "@components/common/Buttons/BackButton";
import { BottomBar, Page, PageContent } from "@styles/common";
import { useNavigate } from "react-router-dom";

export default function ChatDetail() {
  // TODO: ChatRoom으로 route /chatting으로 chat과 분리하면서 변경사항 반영해야 함
  const navigate = useNavigate();
  const { partner, product, chats } = mockChatItem;
  const chatList = Object.entries(chats);

  const goBack = () => {
    navigate(-1);
  };

  return (
    <Page>
      <TopBar
        title={partner.name}
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
        leftBtn={<BackButton color="neutralText" onClick={goBack} />}
        rightBtn={<ChatMoreButton />}
      />
      <ProductBanner productInfo={product} />
      <PageContent>
        {chatList.map(([date, chats]) => (
          <DailyChat key={date} date={date} chats={chats} />
        ))}
      </PageContent>
      <BottomBar>
        <ChatBar />
      </BottomBar>
    </Page>
  );
}

const mockChatItem = {
  partner: {
    id: 1,
    name: "김동동",
    profileImageUrl: "https://via.placeholder.com/150",
  },
  product: {
    thumbnailImgUrl: "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
    title: "아이폰 12 256GB",
    price: 1000000,
  },
  chats: {
    "2021-08-01": [
      {
        id: 1,
        content: "안녕하세요! 한 가지 궁금한 점이 있어서 챗 드려요",
        createdAt: "2021-08-01T12:00:00",
        isMine: false,
      },
    ],
    "2021-08-02": [
      {
        id: 2,
        content: "네 안녕하세요! 답장이 늦어서 죄송합니다.",
        createdAt: "2021-08-02T12:00:00",
        isMine: true,
      },
      {
        id: 3,
        content: "어떤 점이 궁금하신가욤?",
        createdAt: "2021-08-02T12:00:01",
        isMine: true,
      },
      {
        id: 4,
        content: "아이폰 12 256GB이 맞나요? 사진이 귀여운 강아지인데요?",
        createdAt: "2021-08-02T12:30:00",
        isMine: false,
      },
    ],
    "2021-08-03": [
      {
        id: 5,
        content: "네 강아지가 귀여워서 사진을 넣었어요!",
        createdAt: "2021-08-03T12:00:00",
        isMine: false,
      },
    ],
    "2021-08-04": [
      {
        id: 6,
        content: "네 안녕하세요! 답장이 늦어서 죄송합니다.",
        createdAt: "2021-08-04T12:00:00",
        isMine: true,
      },
      {
        id: 7,
        content: "어떤 점이 궁금하신가욤?",
        createdAt: "2021-08-04T12:00:01",
        isMine: true,
      },
      {
        id: 8,
        content: "아이폰 12 256GB이 맞나요? 사진이 귀여운 강아지인데요?",
        createdAt: "2021-08-04T12:30:00",
        isMine: false,
      },
      {
        id: 9,
        content: "네 안녕하세요! 답장이 늦어서 죄송합니다.",
        createdAt: "2021-08-04T12:00:00",
        isMine: true,
      },
      {
        id: 10,
        content: "어떤 점이 궁금하신가욤?",
        createdAt: "2021-08-04T12:00:01",
        isMine: true,
      },
      {
        id: 11,
        content: "아이폰 12 256GB이 맞나요? 사진이 귀여운 강아지인데요?",
        createdAt: "2021-08-04T12:30:00",
        isMine: false,
      },
    ],
  },
};

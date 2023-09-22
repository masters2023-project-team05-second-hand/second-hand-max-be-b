import ChatListItem from "@components/ChatList/ChatListItem";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import TopBar from "@components/TopBar";
import { ROUTE_PATH } from "@router/constants";
import { Main, Page, PageContent } from "@styles/common";
import { useNavigate } from "react-router-dom";

export type ChatItem = {
  id: number;
  userImage: string;
  userName: string;
  thumbnailImage: string;
  lastMessage: string;
  unreadCount: number;
  createdTime: string;
};

export default function ChatList() {
  // Todo: 채팅 목록 조회 api 붙이기
  const navigate = useNavigate();

  const onClickItem = (id: number) => {
    // Todo: 채팅 상세 url 나오면 수정 => 경로에 채팅 id 붙을듯
    navigate(ROUTE_PATH.chat + `/${id}`);
  };

  return (
    <Page>
      <TopBar
        title="채팅"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      <PageContent>
        {mockData.length > 0 ? (
          mockData.map((item) => (
            <ChatListItem key={item.id} onClick={onClickItem} chatItem={item} />
          ))
        ) : (
          <Main>
            <SubInfo>동네 물품 목록이 없습니다</SubInfo>
          </Main>
        )}
      </PageContent>
      <NavigationBar />
    </Page>
  );
}

const mockData: ChatItem[] = [
  {
    id: 1,
    userImage:
      "https://mblogthumb-phinf.pstatic.net/MjAxNzAzMzFfNjEg/MDAxNDkwOTM2NjE4MDQy.eIMvlKaVriccpz8TPo-Wyagr3J6oEz_pRe3S32gADVIg.jQUkh4ws9TiGn6y2h2iu3z5xmyKvxrRMgS0rjJVTRPQg.PNG.jkirby/%EC%9E%A0%EB%A7%8C%EB%B3%B4.png?type=w800",
    userName: "삼만보",
    thumbnailImage: "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
    lastMessage: "안녕하세요! 한 가지 궁금한 점이 있어서 챗드려요",
    unreadCount: 1,
    createdTime: "2023-09-09T10:05:00",
  },
  {
    id: 2,
    userImage:
      "https://i.namu.wiki/i/wkwHbl319sCFlTR6pt9P4AnhauWeYt9a28QtGf50DbR2hAUrZ7hcabdwI3KvPSHJd6JoLJ9PZMONNXcdE0sOqg.webp",
    userName: "파이리",
    thumbnailImage: "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
    lastMessage: "지금 바로 갑니다",
    unreadCount: 1,
    createdTime: "2023-09-16T10:05:00",
  },
  {
    id: 3,
    userImage:
      "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMjRfMjYg/MDAxNTE0MTA3MzM5Mzc5.D9XaME8Z_mRktq7UcaLth2uQpg1v3nC7sFGru4CWwCgg.fBjfTsM9LSH6k9R6b5tQJam4JtSXrC5CAnv7zI6X5Nsg.PNG.bluez3619995/20171224_182048.png?type=w800",
    userName: "꼬부기",
    thumbnailImage: "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
    lastMessage: "왜 채팅을 안보세요? 신고합니다",
    unreadCount: 8,
    createdTime: "2023-09-18T16:07:24",
  },
];

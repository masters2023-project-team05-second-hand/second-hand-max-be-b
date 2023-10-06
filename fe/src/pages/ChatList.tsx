import { useGetChatListQuery } from "@api/chat/queries";
import ChatListItem from "@components/ChatList/ChatListItem";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import TopBar from "@components/TopBar";
import { Error, Loading } from "@components/common/Guide";
import { Main, Page, PageContent } from "@styles/common";
import { useParams } from "react-router-dom";

export default function ChatList() {
  const { productId } = useParams();
  const numberProductId = Number(productId);

  const {
    data: chatList,
    isSuccess,
    isLoading,
    isError,
  } = useGetChatListQuery(numberProductId);

  return (
    <Page>
      <TopBar
        title="채팅"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      <PageContent>
        {isSuccess && (
          <>
            {chatList.length > 0 ? (
              chatList.map((item) => (
                <ChatListItem key={item.roomId} chatItem={item} />
              ))
            ) : (
              <Main>
                <SubInfo>채팅 목록이 없습니다</SubInfo>
              </Main>
            )}
          </>
        )}
        {isLoading && (
          <Loading
            messages={[
              "채팅 목록을 불러오는 중입니다...",
              "새로고침을 하지 마세요!",
            ]}
          />
        )}
        {isError && (
          <Error
            messages={[
              "채팅 목록 조회에 실패했습니다.",
              "새로고침을 하거나 다시 시도해주세요.",
            ]}
          />
        )}
      </PageContent>
      <NavigationBar />
    </Page>
  );
}

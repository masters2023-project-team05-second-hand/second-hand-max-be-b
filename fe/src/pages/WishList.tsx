import { userKeys } from "@api/queryKeys";
import {
  useUserLikeCategories,
  useUserWishlistInfiniteQuery,
} from "@api/user/queries";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import Products from "@components/ProductList/Products";
import TopBar from "@components/TopBar";
import { Error, Loading } from "@components/common/Guide";
import { LoadingSpinner } from "@components/common/LoadingSpinner";
import { TabButtons } from "@components/common/TabButtons";
import useDrag from "@hooks/useDrag";
import { useIntersect } from "@hooks/useIntersect";
import { Main, Page, PageContent, Target } from "@styles/common";
import { useState } from "react";
import { DEFAULT_TAB } from "store/constants";

export default function WishList() {
  const { data: userCategories, isSuccess: isUserCategoriesSuccess } =
    useUserLikeCategories();
  const [activeTabId, setActiveTabId] = useState(DEFAULT_TAB.id);
  const {
    data: categoryProducts,
    status,
    hasNextPage,
    isFetching,
    fetchNextPage,
  } = useUserWishlistInfiniteQuery(activeTabId);
  const { ref, onDragStart, onDragEnd, onDragMove } =
    useDrag<HTMLUListElement>();

  const onTabClick = (tabId: number) => {
    setActiveTabId(tabId);
  };

  const targetRef = useIntersect(() => {
    if (hasNextPage) {
      fetchNextPage();
    }
  });

  const isEmpty = categoryProducts?.pages[0].products.length === 0;

  return (
    <Page>
      <TopBar
        title="관심 목록"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      {status === "loading" && (
        <Loading
          messages={[
            "상품 목록을 불러오는 중입니다.",
            "새로고침을 하지 마세요!",
          ]}
        />
      )}
      {status === "error" && (
        <Error
          messages={[
            "상품 목록을 불러오는데 실패했어요.",
            "잠시 후 다시 시도해주세요.",
          ]}
        />
      )}
      {status === "success" &&
        (isEmpty ? (
          <Main>
            <SubInfo>관심 상품이 없습니다</SubInfo>
          </Main>
        ) : (
          <PageContent>
            {isUserCategoriesSuccess && (
              <TabButtons
                ref={ref}
                activeTabId={activeTabId}
                tabList={[DEFAULT_TAB, ...userCategories]}
                onTabClick={onTabClick}
                onMouseDown={onDragStart}
                onMouseMove={onDragMove}
                onMouseUp={onDragEnd}
                onMouseLeave={onDragEnd}
              />
            )}
            <Products
              productList={categoryProducts.pages.map((page) => page.products)}
              invalidateQueryKey={
                userKeys.wishlistProduct(activeTabId).queryKey
              }
            />
            {isFetching ? <LoadingSpinner /> : <Target ref={targetRef} />}
          </PageContent>
        ))}
      <NavigationBar />
    </Page>
  );
}

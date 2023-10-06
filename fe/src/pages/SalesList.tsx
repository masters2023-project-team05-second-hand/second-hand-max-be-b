import { userKeys } from "@api/queryKeys";
import { useUserSalesInfiniteQuery } from "@api/user/queries";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import Products from "@components/ProductList/Products";
import TopBar from "@components/TopBar";
import { Error, Loading } from "@components/common/Guide";
import { LoadingSpinner } from "@components/common/LoadingSpinner";
import { TabButtons } from "@components/common/TabButtons";
import { useIntersect } from "@hooks/useIntersect";
import { Main, Page, PageContent, Target } from "@styles/common";
import { useState } from "react";
import { useStatusesValue } from "store";
import { DEFAULT_TAB } from "store/constants";

export default function SalesList() {
  const productStatuses = useStatusesValue();
  const [activeTabId, setActiveTabId] = useState(DEFAULT_TAB.id);
  const {
    data: salesProducts,
    status,
    hasNextPage,
    isFetching,
    fetchNextPage,
  } = useUserSalesInfiniteQuery(productStatuses, activeTabId);

  const targetRef = useIntersect(() => {
    if (hasNextPage) {
      fetchNextPage();
    }
  });

  const isEmpty = salesProducts?.pages[0].products.length === 0;
  const onTabClick = (tabId: number) => {
    setActiveTabId(tabId);
  };

  return (
    <Page>
      <TopBar
        title="판매 내역"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      <PageContent>
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
        {!!productStatuses.length && (
          <TabButtons
            activeTabId={activeTabId}
            tabList={[DEFAULT_TAB, ...productStatuses]}
            onTabClick={onTabClick}
          />
        )}
        {status === "success" && (
          <>
            {isEmpty ? (
              <>
                <Main>
                  <SubInfo>판매 내역이 없습니다</SubInfo>
                </Main>
              </>
            ) : (
              <Products
                productList={salesProducts.pages.map((page) => page.products)}
                invalidateQueryKey={userKeys.salesProduct(activeTabId).queryKey}
              />
            )}
          </>
        )}
        {isFetching ? <LoadingSpinner /> : <Target ref={targetRef} />}
      </PageContent>
      <NavigationBar />
    </Page>
  );
}

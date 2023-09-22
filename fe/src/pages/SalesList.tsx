import { useProductStatusesQuery } from "@api/product/queries";
import { useUserSalesInfiniteQuery } from "@api/user/queries";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import Products from "@components/ProductList/Products";
import TopBar from "@components/TopBar";
import { TabButtons } from "@components/common/TabButtons";
import { useIntersect } from "@hooks/useIntersect";
import { Main, Page, PageContent, Target } from "@styles/common";
import { useState } from "react";
import { DEFAULT_TAB } from "store/constants";

export default function SalesList() {
  const [activeTabId, setActiveTabId] = useState(DEFAULT_TAB.id);
  const { data: productStatuses, isSuccess: isGetStatusesSuccess } =
    useProductStatusesQuery();
  const {
    data: salesProducts,
    isSuccess: isSalesProductsSuccess,
    hasNextPage,
    isFetching,
    fetchNextPage,
  } = useUserSalesInfiniteQuery(activeTabId);

  const ref = useIntersect((entry, observer) => {
    observer.unobserve(entry.target);
    if (hasNextPage && !isFetching) {
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
        {isGetStatusesSuccess && (
          <TabButtons
            activeTabId={activeTabId}
            tabList={[DEFAULT_TAB, ...productStatuses]}
            onTabClick={onTabClick}
          />
        )}
        {isSalesProductsSuccess && (
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
              />
            )}
          </>
        )}
        <Target ref={ref} />
      </PageContent>
      <NavigationBar />
    </Page>
  );
}

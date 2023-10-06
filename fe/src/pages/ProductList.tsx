import { useGetProductListInfiniteQuery } from "@api/product/queries";
import { productKeys } from "@api/queryKeys";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import ProductListFAB from "@components/ProductList/ProductListFAB";
import ProductListHeader from "@components/ProductList/ProductListHeader";
import Products from "@components/ProductList/Products";
import { Error, Loading } from "@components/common/Guide";
import { LoadingSpinner } from "@components/common/LoadingSpinner";
import { useIntersect } from "@hooks/useIntersect";
import { Main, Page, Target } from "@styles/common";
import { useCurrentAddressIdValue, useCurrentCategoryIdValue } from "store";

export default function ProductList() {
  const categoryId = useCurrentCategoryIdValue();
  const currentAddressId = useCurrentAddressIdValue();

  const {
    data: productList,
    status,
    hasNextPage,
    isFetching,
    fetchNextPage,
  } = useGetProductListInfiniteQuery({
    addressId: currentAddressId,
    categoryId,
  });

  const targetRef = useIntersect(() => {
    if (hasNextPage) {
      fetchNextPage();
    }
  });

  const isEmpty = productList?.pages[0].products.length === 0;

  return (
    <Page>
      <ProductListHeader />
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
      {status === "success" && (
        <>
          {isEmpty ? (
            <>
              <Main>
                <SubInfo>동네 물품 목록이 없습니다</SubInfo>
              </Main>
            </>
          ) : (
            <Products
              productList={productList.pages.map((page) => page.products)}
              invalidateQueryKey={
                productKeys.products(currentAddressId, categoryId).queryKey
              }
            />
          )}
        </>
      )}
      <ProductListFAB />
      {isFetching ? <LoadingSpinner /> : <Target ref={targetRef} />}
      <NavigationBar />
    </Page>
  );
}

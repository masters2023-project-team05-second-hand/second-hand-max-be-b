import { useGetProductListInfiniteQuery } from "@api/product/queries";
import NavigationBar from "@components/NavigationBar";
import { SubInfo } from "@components/ProductDetail/common.style";
import ProductListFAB from "@components/ProductList/ProductListFAB";
import ProductListHeader from "@components/ProductList/ProductListHeader";
import Products from "@components/ProductList/Products";
import { Error, Loading } from "@components/common/Guide";
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
    categoryId: categoryId,
  });

  const ref = useIntersect((entry, observer) => {
    observer.unobserve(entry.target);
    if (hasNextPage && !isFetching) {
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
            />
          )}
        </>
      )}
      <ProductListFAB />
      <Target ref={ref} />
      <NavigationBar />
    </Page>
  );
}

import { ProductItem } from "@api/type";
import ProductListItem from "@components/ProductListItem";
import { styled } from "styled-components";

export default function Products({
  productList,
  invalidateQueryKey,
}: {
  productList: ProductItem[][];
  invalidateQueryKey: readonly unknown[];
}) {
  return (
    <StyledProductList>
      {productList.map((products) =>
        products.map((productItem: ProductItem) => (
          <ProductListItem
            key={productItem.productId}
            {...{ productItem, invalidateQueryKey }}
          />
        ))
      )}
    </StyledProductList>
  );
}

const StyledProductList = styled.ul`
  width: 100%;
`;

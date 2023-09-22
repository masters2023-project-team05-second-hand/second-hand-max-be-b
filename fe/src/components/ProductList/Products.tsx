import { ProductItem } from "@api/type";
import ProductListItem from "@components/ProductListItem";
import { styled } from "styled-components";

export default function Products({
  productList,
}: {
  productList: ProductItem[][];
}) {
  return (
    <StyledProductList>
      {productList.map((products) =>
        products.map((productItem: ProductItem) => (
          <ProductListItem
            key={productItem.productId}
            productItem={productItem}
          />
        ))
      )}
    </StyledProductList>
  );
}

const StyledProductList = styled.ul`
  width: 100%;
`;

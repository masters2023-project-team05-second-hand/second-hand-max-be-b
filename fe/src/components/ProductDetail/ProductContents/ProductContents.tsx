import { ProductInfo as ProductInfoType, ProductStats } from "@api/type";
import ProductInfo from "@components/ProductDetail/ProductContents/ProductInfo";
import SellerInfo from "@components/ProductDetail/ProductContents/SellerInfo";
import { SubInfo } from "@components/ProductDetail/common.style";
import styled from "styled-components";
import ProductStatus from "../ProductStatus";

export default function ProductContents({
  productInfo,
  stats,
  isSeller,
}: {
  productInfo: ProductInfoType;
  stats: ProductStats;
  isSeller: boolean;
}) {
  return (
    <StyledProductContents>
      <SellerInfo sellerName={productInfo.seller.nickname} />
      {isSeller && <ProductStatus currentStatusId={productInfo.status} />}
      <ProductInfo
        title={productInfo.title}
        categoryName={productInfo.category.name}
        createdTime={productInfo.createdTime}
        contents={productInfo.contents}
      />
      <SubInfo className="product-stats-subInfo">
        <span>채팅 {stats.chatCount}</span>
        <span>관심 {stats.wishCount}</span>
        <span>조회 {stats.viewCount}</span>
      </SubInfo>
    </StyledProductContents>
  );
}

const StyledProductContents = styled.div`
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;

  .product-stats-subInfo {
    gap: 0.5rem;
  }
`;

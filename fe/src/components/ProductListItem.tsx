import { ProductItem } from "@api/type";
import { ReactComponent as HeartIcon } from "@assets/icon/heart.svg";
import { ReactComponent as MessageIcon } from "@assets/icon/message.svg";
import ProductStatus from "@components/ProductStatus";
import { ROUTE_PATH } from "@router/constants";
import { useNavigate } from "react-router-dom";
import { useMemberValue } from "store";
import { styled } from "styled-components";
import ProductMoreButton from "./ProductMoreButton";
import { convertPastTimestamp } from "@utils/time";

type ProductListItemProps = {
  productItem: ProductItem;
};

export default function ProductListItem({ productItem }: ProductListItemProps) {
  const navigate = useNavigate();
  const { id: memberId } = useMemberValue();

  const onProductClick = () => {
    navigate(`${ROUTE_PATH.detail}/${productItem.productId}`);
  };

  const isSeller = productItem.sellerId === memberId;
  const productPrice = productItem.price?.toLocaleString("ko-KR");

  return (
    <StyledProductListItem onClick={onProductClick}>
      <Product>
        <ProductImage src={productItem.thumbnailUrl} />
        <ProductInfo>
          <div className="info-main">
            <div className="info-top">
              <TextDefault>{productItem.title}</TextDefault>
              {isSeller && (
                <ProductMoreButton
                  productId={productItem.productId + ""}
                  currentStatusId={productItem.statusId}
                />
              )}
            </div>
            <div className="info-middle">
              <TextWeak>{productItem.addressName}</TextWeak>
              <TextWeak>・</TextWeak>
              <TextWeak>
                {convertPastTimestamp(productItem.createdTime)}
              </TextWeak>
            </div>
            <div className="info-bottom">
              <ProductStatus id={productItem.statusId} />
              <TextBold>
                {productPrice ? `${productPrice} 원` : "가격미정"}
              </TextBold>
            </div>
          </div>
          <div className="info-history">
            {!!productItem.stats.chatCount && (
              <>
                <History>
                  <MessageIcon />
                  <TextWeak>{productItem.stats.chatCount}</TextWeak>
                </History>
              </>
            )}
            {!!productItem.stats.wishCount && (
              <>
                <History>
                  <HeartIcon />
                  <TextWeak>{productItem.stats.wishCount}</TextWeak>
                </History>
              </>
            )}
          </div>
        </ProductInfo>
      </Product>
    </StyledProductListItem>
  );
}

const StyledProductListItem = styled.li`
  border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};

  &:last-child {
    border-bottom: none;
  }
`;

const Product = styled.div`
  margin: 0 16px;
  padding: 16px 0;
  height: 152px;
  box-sizing: border-box;
  display: flex;
  gap: 16px;

  &:not(&:has(.more:hover)):hover {
    cursor: pointer;
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }
`;

const ProductImage = styled.img`
  width: 120px;
  height: 120px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
`;

const ProductInfo = styled.div`
  flex: 1;
  height: 120px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;

  .info-main {
    display: flex;
    flex-direction: column;
    gap: 4px;

    .info-top {
      display: flex;
      justify-content: space-between;

      .more {
        &:hover {
          cursor: pointer;
          filter: ${({ theme: { filter } }) => filter.neutralTextWeak};
        }
      }
    }

    .info-middle {
      display: flex;
    }

    .info-bottom {
      display: flex;
      gap: 4px;
    }
  }

  .info-history {
    display: flex;
    gap: 8px;
    margin-left: auto;
  }
`;

const TextDefault = styled.span`
  display: block;
  max-width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: ${({ theme: { color } }) => color.neutralText};
  font: ${({ theme: { font } }) => font.displayDefault16};
`;

const TextWeak = styled.span`
  color: ${({ theme: { color } }) => color.neutralTextWeak};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

const TextBold = styled.span`
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.displayStrong16};
`;

const History = styled.div`
  display: flex;

  align-items: center;
  gap: 4px;

  svg {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextWeak};
  }
`;

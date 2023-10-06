import {
  useDeleteProductQuery,
  useMutateProductStatus,
} from "@api/product/queries";
import { ProductItem } from "@api/type";
import { ReactComponent as HeartIcon } from "@assets/icon/heart.svg";
import { ReactComponent as MessageIcon } from "@assets/icon/message.svg";
import ProductStatus from "@components/ProductStatus";
import { ROUTE_PATH } from "@router/constants";
import { TextDefault, TextWeak } from "@styles/common";
import { getLastWord } from "@utils/index";
import { convertPastTimestamp } from "@utils/time";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMemberValue, useStatusesValue } from "store";
import { styled } from "styled-components";
import ProductMoreButton from "./ProductMoreButton";
import Alert from "./common/Alert/Alert";
import { MenuItemInfo } from "./common/Menu/type";
import PriceText from "./common/PriceText";

type ProductListItemProps = {
  productItem: ProductItem;
  invalidateQueryKey: readonly unknown[];
};

export default function ProductListItem({
  productItem,
  invalidateQueryKey,
}: ProductListItemProps) {
  const navigate = useNavigate();
  const { id: memberId } = useMemberValue();
  const productStatuses = useStatusesValue();
  const [isDeleteAlertOpen, setIsDeleteAlertOpen] = useState(false);

  const { onDeleteProduct } = useDeleteProductQuery({
    productId: productItem.productId,
    invalidateQueryKey,
  });
  const { mutate: mutateProductStatus } = useMutateProductStatus({
    invalidateQueryKey,
  });

  const onProductClick = () => {
    navigate(`${ROUTE_PATH.detail}/${productItem.productId}`);
  };

  const getProductStatusList = (): MenuItemInfo[] => {
    return productStatuses.length
      ? productStatuses
          .filter((status) => status.id !== productItem.statusId)
          .map((productStatus) => ({
            name: `${productStatus.type} 상태로 전환`,
            onClick: () => {
              mutateProductStatus({
                productId: productItem.productId,
                statusId: productStatus.id,
              });
            },
          }))
      : [];
  };

  const openDeleteAlert = () => setIsDeleteAlertOpen(true);
  const closeDeleteAlert = () => setIsDeleteAlertOpen(false);

  const isSeller = productItem.sellerId === memberId;

  return (
    <>
      <StyledProductListItem onClick={onProductClick}>
        <Product>
          <ProductImage src={productItem.thumbnailUrl} />
          <ProductInfo>
            <div className="info-main">
              <div className="info-top">
                <TextDefault>{productItem.title}</TextDefault>
                {isSeller && (
                  <ProductMoreButton
                    productId={productItem.productId}
                    statusListItems={getProductStatusList()}
                    openDeleteAlert={openDeleteAlert}
                  />
                )}
              </div>
              <div className="info-middle">
                <TextWeak>{getLastWord(productItem.addressName)}</TextWeak>
                <TextWeak>・</TextWeak>
                <TextWeak>
                  {convertPastTimestamp(productItem.createdTime)}
                </TextWeak>
              </div>
              <div className="info-bottom">
                <ProductStatus id={productItem.statusId} />
                <PriceText productPrice={productItem.price} />
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
      {isDeleteAlertOpen && (
        <Alert
          message={"상품을 삭제하시겠어요?"}
          onDeleteClick={onDeleteProduct}
          closeAlertHandler={closeDeleteAlert}
        />
      )}
    </>
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

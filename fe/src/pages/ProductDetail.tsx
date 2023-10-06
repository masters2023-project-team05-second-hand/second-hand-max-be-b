import {
  useDeleteProductQuery,
  useProductDetailQuery,
} from "@api/product/queries";
import ChatButton from "@components/ProductDetail/Buttons/ChatButton/ChatButton";
import MoreButton from "@components/ProductDetail/Buttons/MoreButton";
import ProductLikeButton from "@components/ProductDetail/Buttons/ProductLikeButton";
import ProductContents from "@components/ProductDetail/ProductContents/ProductContents";
import ProductImageList from "@components/ProductDetail/ProductImageList";
import TopBar from "@components/TopBar";
import BackButton from "@components/common/Buttons/BackButton";
import PriceText from "@components/common/PriceText";
import useAnimation from "@hooks/useAnimation";
import useWatchScroll from "@hooks/useWatchScroll";
import { ROUTE_PATH } from "@router/constants";
import { slide } from "@styles/animate";
import { BottomBar, Page } from "@styles/common";
import { delay } from "@utils/index";
import { AnimatePresence, motion } from "framer-motion";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useMemberValue } from "store";
import { SLIDE_TIME } from "store/constants";
import styled from "styled-components";

export default function ProductDetail() {
  const member = useMemberValue();
  const { scrollY, ref } = useWatchScroll();
  const navigate = useNavigate();
  const location = useLocation();
  const { isAnimating, onLeavePage } = useAnimation();

  const { productId } = useParams();
  const numberProductId = Number(productId);

  const { data: productDetailInfo, isSuccess: isProductDetailSuccess } =
    useProductDetailQuery(numberProductId, !!productId);

  const goBack = () => {
    navigate(location.state?.prevRoute ?? -1);
  };

  const onClickBack = async () => {
    onLeavePage();
    await delay(SLIDE_TIME);
    goBack();
  };

  const onClickEdit = async () => {
    onLeavePage();
    await delay(SLIDE_TIME);
    navigate(`${ROUTE_PATH.edit}/${productId}`);
  };

  const { onDeleteProduct } = useDeleteProductQuery({
    productId: numberProductId,
    onSuccess: goBack,
  });

  const isScroll = !!scrollY && scrollY > 0;
  const isSeller = member.id === productDetailInfo?.product.seller.id;

  return (
    <Page ref={ref}>
      <TopBar
        backgroundColor="accentPrimary"
        isScrolled={isScroll}
        leftBtn={<BackButton color="accentText" onClick={onClickBack} />}
        rightBtn={
          isSeller && (
            <MoreButton onEdit={onClickEdit} onDelete={onDeleteProduct} />
          )
        }
      />
      {isProductDetailSuccess && (
        <AnimatePresence>
          {isAnimating && (
            <StyledProductDetail
              initial="initial"
              animate="in"
              exit="out"
              variants={slide}>
              <ProductImageList productImages={productDetailInfo.images} />
              <ProductContents
                productInfo={productDetailInfo.product}
                stats={productDetailInfo.stats}
                isSeller={isSeller}
              />
            </StyledProductDetail>
          )}
        </AnimatePresence>
      )}
      <BottomBar>
        <ButtonContainer>
          <LeftWrapper>
            <ProductLikeButton />
            {productDetailInfo && (
              <PriceText productPrice={productDetailInfo.product.price} />
            )}
          </LeftWrapper>
          {productDetailInfo && (
            <ChatButton
              isSeller={isSeller}
              chatInfo={{
                product: {
                  productId: numberProductId,
                  title: productDetailInfo.product.title,
                  price: productDetailInfo.product.price,
                  thumbnailUrl: productDetailInfo.images[0].url,
                },
                seller: productDetailInfo.product.seller,
                chatCount: productDetailInfo.stats.chatCount,
              }}
            />
          )}
        </ButtonContainer>
      </BottomBar>
    </Page>
  );
}

const StyledProductDetail = styled(motion.div)`
  position: absolute;
  top: 0;
  width: 100%;
`;

const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 16px;
`;

const LeftWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

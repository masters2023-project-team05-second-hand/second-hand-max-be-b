import {
  useDeleteProductQuery,
  useProductDetailQuery,
} from "@api/product/queries";
import BackButton from "@components/common/Buttons/BackButton";
import ChatButton from "@components/ProductDetail/Buttons/ChatButton";
import MoreButton from "@components/ProductDetail/Buttons/MoreButton";
import ProductLikeButton from "@components/ProductDetail/Buttons/ProductLikeButton";
import ProductContents from "@components/ProductDetail/ProductContents/ProductContents";
import ProductImageList from "@components/ProductDetail/ProductImageList";
import TopBar from "@components/TopBar";
import useAnimation from "@hooks/useAnimation";
import useScroll from "@hooks/useScroll";
import { useToast } from "@hooks/useToast";
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
  const { scrollY, ref } = useScroll();
  const { toast } = useToast();
  const navigate = useNavigate();
  const location = useLocation();
  const { isAnimating, onLeavePage } = useAnimation();

  const { productId } = useParams();
  const numberProductId = Number(productId);

  const { data: productDetailInfo, isSuccess: isProductDetailSuccess } =
    useProductDetailQuery(numberProductId, !!productId);

  const showNoChatPartner = () => {
    toast({
      type: "info",
      title: "상품 상세 목록 조회 완료",
      message: "채팅한 이웃이 없습니다",
    });
  };

  const goChatPage = () => {
    // TODO: 채팅 목록 조회 api 나오면 수정
    if (isSeller) {
      navigate(ROUTE_PATH.chat);
    } else {
      navigate(ROUTE_PATH.chat);
    }
  };

  const onClickChat = () => {
    if (!isProductDetailSuccess) {
      return;
    }

    if (isSeller && !!productDetailInfo.stats.chatCount) {
      showNoChatPartner();
      return;
    }

    goChatPage();
  };

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
    navigate(ROUTE_PATH.edit + `/${productId}`);
  };

  const { onDeleteProduct } = useDeleteProductQuery({
    productId: numberProductId,
    onSuccess: goBack,
  });

  const isScroll = !!scrollY && scrollY > 0;
  const isSeller = member.id === productDetailInfo?.product.seller.id;
  const productPrice =
    productDetailInfo?.product.price?.toLocaleString("ko-KR");

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
            {isProductDetailSuccess && (
              <span>{productPrice ? `${productPrice} 원` : "가격미정"}</span>
            )}
          </LeftWrapper>
          <ChatButton
            onClick={onClickChat}
            isSeller={isSeller}
            chatCount={productDetailInfo?.stats.chatCount}
          />
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

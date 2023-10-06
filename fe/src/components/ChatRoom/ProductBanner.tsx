import { ChatItemProduct } from "@api/type";
import PriceText from "@components/common/PriceText";
import { TextDefault } from "@styles/common";
import { HEIGHT } from "@styles/constants";
import styled from "styled-components";

export default function ProductBanner({
  product,
}: {
  product: ChatItemProduct;
}) {
  const { thumbnailUrl, title, price } = product;

  return (
    <StyledProductBanner>
      <Image src={thumbnailUrl} alt={`${title} 섬네일 이미지`} />
      <div>
        <TextDefault>{title}</TextDefault>
        <PriceText productPrice={price} />
      </div>
    </StyledProductBanner>
  );
}

const StyledProductBanner = styled.div`
  position: sticky;
  top: ${HEIGHT.topBar}px;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  z-index: 1;
  height: 80px;
  padding: 0 1rem;
  gap: 0.5rem;
  display: flex;
  align-items: center;
  outline: 1px solid ${({ theme: { color } }) => color.neutralBorder};
`;

const Image = styled.img`
  width: 48px;
  height: 48px;
  border-radius: 8px;
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
`;

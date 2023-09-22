import { TextBold, TextDefault } from "@styles/common";
import { HEIGHT } from "@styles/constants";
import styled from "styled-components";

export default function ProductBanner({
  productInfo,
}: {
  productInfo: {
    thumbnailImgUrl: string;
    title: string;
    price: number;
  };
}) {
  const { thumbnailImgUrl, title, price } = productInfo;

  return (
    <StyledProductBanner>
      <Image src={thumbnailImgUrl} alt={`${title} 섬네일 이미지`} />
      <div>
        <TextDefault>{title}</TextDefault>
        <TextBold>{`${price.toLocaleString("ko-KR")}원`}</TextBold>
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

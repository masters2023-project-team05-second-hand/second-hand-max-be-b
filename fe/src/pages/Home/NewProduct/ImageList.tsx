import Button from "@components/common/Buttons/Button";
import { styled } from "styled-components";

export default function ImageList() {
  return (
    <>
      <StyledImageList>
        <Button />
        <ImageItem />
      </StyledImageList>
    </>
  );
}

const StyledImageList = styled.div`
  display: flex;
  flex-direction: row;
  padding-bottom: 16px;
  border-bottom: ${({ theme: { color } }) =>
    `1px solid ${color.neutralBorder}`};
`;

const ImageItem = styled.div``;

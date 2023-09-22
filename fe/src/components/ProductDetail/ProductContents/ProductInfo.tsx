import { convertPastTimestamp } from "@utils/time";
import styled from "styled-components";
import { SubInfo } from "../common.style";

export default function ProductInfo({
  title,
  categoryName,
  createdTime,
  contents,
}: {
  title: string;
  categoryName: string;
  createdTime: string;
  contents: string;
}) {
  return (
    <StyledProductInfo>
      <Title>{title}</Title>
      <SubInfo>
        {categoryName} ・ {convertPastTimestamp(createdTime)}
      </SubInfo>
      <Contents>{contents}</Contents>
    </StyledProductInfo>
  );
}

const StyledProductInfo = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Title = styled.h1`
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const Contents = styled.p`
  font: ${({ theme: { font } }) => font.displayDefault16};
  color: ${({ theme: { color } }) => color.neutralText};
`;

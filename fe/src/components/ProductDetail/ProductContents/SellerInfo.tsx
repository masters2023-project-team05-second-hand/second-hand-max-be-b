import styled from "styled-components";

export default function SellerInfo({ sellerName }: { sellerName: string }) {
  return (
    <StyledSellerInfo>
      <div className="text-wrapper">
        <Title>판매자 정보</Title>
        <SellerName>{sellerName}</SellerName>
      </div>
    </StyledSellerInfo>
  );
}

const StyledSellerInfo = styled.div`
  width: 360px;
  height: 56px;
  margin: 0 auto;
  border-radius: ${({ theme: { radius } }) => radius[12]};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundWeak};

  .text-wrapper {
    padding: 16px;
    display: flex;
    justify-content: space-between;
  }
`;

const Title = styled.h2`
  font: ${({ theme: { font } }) => font.displayDefault16};
  color: ${({ theme: { color } }) => color.neutralText};
`;

const SellerName = styled.span`
  font: ${({ theme: { font } }) => font.displayStrong16};
  color: ${({ theme: { color } }) => color.neutralText};
`;

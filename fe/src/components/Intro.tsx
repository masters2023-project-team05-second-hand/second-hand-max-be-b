import { SubTitle } from "@styles/common";
import styled from "styled-components";

export default function Intro() {
  return (
    <StyledIntro>
      <SubTitle>오만가지 중고거래 세상</SubTitle>
      <Title>가지마켓</Title>
      <div className="text-wrapper">
        <Text>오만가지 중고거래 세상 가지마켓에 오신걸 환영해요!</Text>
        <div className="team">
          <Text>Codesquad team 05 친해지길바래</Text>
          <Text>감귤 나그 위즈 조이 지니 토코</Text>
        </div>
      </div>
    </StyledIntro>
  );
}

const StyledIntro = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  .text-wrapper {
    margin-top: 16px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
  }

  .team {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
`;

const Title = styled.h1`
  font: ${({ theme: { font } }) => font.displayStrong32};
  color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Text = styled.p`
  font: ${({ theme: { font } }) => font.availableDefault12};
  color: ${({ theme: { color } }) => color.neutralText};
`;

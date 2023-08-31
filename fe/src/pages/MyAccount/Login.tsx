import gajiCharacter from "@assets/image/gaji.png";
import Intro from "@components/Intro";
import {
  GithubButton,
  KakaoButton,
} from "@components/common/Buttons/SocialButtons";
import { Main } from "@styles/common";
import { KAKAO_LOGIN_URL } from "api/constants";
import styled from "styled-components";

export default function Login() {
  const onKakaoLogin = () => {
    window.location.href = KAKAO_LOGIN_URL;
  };

  return (
    <Main>
      <Intro />
      <img src={gajiCharacter} alt="가지 캐릭터 이미지" width={"100%"} />
      <ButtonWrapper>
        <KakaoButton onClick={onKakaoLogin} />
        <GithubButton />
      </ButtonWrapper>
    </Main>
  );
}

const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

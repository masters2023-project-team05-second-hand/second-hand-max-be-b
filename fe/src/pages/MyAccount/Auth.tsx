import LoadingIndicator from "@assets/image/loading.gif";
import { ROUTE_PATH } from "@router/constants";
import { SubTitle } from "@styles/common";
import { postSocialLogin } from "api";
import { useSetAtom } from "jotai";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { addressListAtom, memberAtom, tokensAtom } from "store";
import styled from "styled-components";

export function Auth() {
  const { provider } = useParams<{ provider: "kakao" | "github" }>();
  const accessCode = new URL(window.location.href).searchParams.get("code");

  const navigate = useNavigate();

  const setMember = useSetAtom(memberAtom);
  const setTokens = useSetAtom(tokensAtom);
  const setAddresses = useSetAtom(addressListAtom);

  // TODO: tanstack-query 사용해서 로그인 처리
  useEffect(() => {
    const getMember = async () => {
      if (accessCode && provider) {
        const {
          data: { tokens, addresses, member },
        } = await postSocialLogin(provider, { accessCode });
        const isFirstLogin = addresses?.length === 0;

        localStorage.setItem("accessToken", tokens.accessToken);
        localStorage.setItem("refreshToken", tokens.refreshToken);
        localStorage.setItem("addresses", JSON.stringify(addresses));
        localStorage.setItem("member", JSON.stringify(member));

        setMember(member);
        setTokens(tokens);
        setAddresses(addresses);

        isFirstLogin
          ? navigate(ROUTE_PATH.register)
          : navigate(ROUTE_PATH.home);
      }
    };

    getMember();
  }, [accessCode, navigate, provider, setAddresses, setMember, setTokens]);

  return (
    <Loading>
      <img src={LoadingIndicator} alt="loading-indicator" />
      <SubTitle>로그인중입니다...</SubTitle>
      <SubTitle>새로고침을 하지 마세요!</SubTitle>
    </Loading>
  );
}

const Loading = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 100px;

  img {
    margin: 30px;
  }
`;

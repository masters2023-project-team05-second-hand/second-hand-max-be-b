import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Error, Loading } from "@components/common/Guide";
import { ROUTE_PATH } from "@router/constants";
import { Page } from "@styles/common";
import { useQuery } from "@tanstack/react-query";
import { postSocialLogin } from "api/user";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSetIsLogin } from "store";

export function Auth() {
  const { provider } = useParams<{ provider: "kakao" | "github" }>();
  const accessCode = new URL(window.location.href).searchParams.get("code");
  const navigate = useNavigate();
  const setIsLogin = useSetIsLogin();

  const { data, isLoading, isSuccess, isError } = useQuery({
    queryKey: ["socialLogin"],
    enabled: !!accessCode && !!provider,
    queryFn: () => postSocialLogin(provider!, accessCode!), // enabled 조건으로 보장 가능
  });

  useEffect(() => {
    if (isSuccess) {
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);

      setIsLogin(true);
      navigate(ROUTE_PATH.home);
    }
  }, [isSuccess, data, navigate, setIsLogin]);

  return (
    <Page>
      <TopBar
        title="내 계정"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      {isLoading && (
        <Loading messages={["로그인중입니다...", "새로고침을 하지 마세요!"]} />
      )}
      {isError && (
        <Error
          messages={[
            "로그인에 실패했습니다.",
            "새로고침을 하거나 다시 시도해주세요.",
          ]}
        />
      )}
      <NavigationBar />
    </Page>
  );
}

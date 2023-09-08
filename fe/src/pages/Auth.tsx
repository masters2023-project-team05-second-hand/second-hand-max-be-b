import { useUserInfoQuery } from "@api/queries";
import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Error, Loading } from "@components/common/Guide";
import { ROUTE_PATH } from "@router/constants";
import { Page } from "@styles/common";
import { useQuery } from "@tanstack/react-query";
import { postSocialLogin } from "api";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSetAddresses, useSetMember } from "store";

export function Auth() {
  const { provider } = useParams<{ provider: "kakao" | "github" }>();
  const accessCode = new URL(window.location.href).searchParams.get("code");
  const navigate = useNavigate();

  const setMember = useSetMember();
  const setAddresses = useSetAddresses();

  const { data, isLoading, isSuccess, isError } = useQuery({
    queryKey: ["socialLogin"],
    enabled: !!accessCode && !!provider,
    queryFn: () => postSocialLogin(provider!, accessCode!), // enabled 조건으로 보장 가능
  });

  const [memberResult, memberAddressResult] = useUserInfoQuery({
    enabled: isSuccess,
  });

  useEffect(() => {
    if (isSuccess) {
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
    }
  }, [isSuccess, data]);

  useEffect(() => {
    if (memberResult.isSuccess) {
      setMember(memberResult.data);
    }
  }, [memberResult, setMember]);

  useEffect(() => {
    if (memberAddressResult.isSuccess) {
      const userAddressesInfo = memberAddressResult.data;
      setAddresses(
        userAddressesInfo.map((address) => ({
          id: address.id,
          name: address.name,
        }))
      );
    }
  }, [memberAddressResult, setAddresses]);

  useEffect(() => {
    if (memberAddressResult.isSuccess && memberResult.isSuccess) {
      memberAddressResult.data.length
        ? navigate(ROUTE_PATH.home)
        : navigate(ROUTE_PATH.register);
    }
  }, [memberAddressResult, memberResult, navigate]);

  return (
    <Page>
      <TopBar
        title="내 계정"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      {(isLoading ||
        memberAddressResult.isLoading ||
        memberResult.isLoading) && (
        <Loading messages={["로그인중입니다...", "새로고침을 하지 마세요!"]} />
      )}
      {(isError || memberAddressResult.isError || memberResult.isError) && (
        <>
          <Error
            messages={[
              "로그인에 실패했습니다.",
              "새로고침을 하거나 다시 시도해주세요.",
            ]}
          />
          <NavigationBar />
        </>
      )}
    </Page>
  );
}

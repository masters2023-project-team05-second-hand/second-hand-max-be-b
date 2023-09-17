import UserAccount from "@components/UserAccount";
import Button from "@components/common/Buttons/Button";
import { Main } from "@styles/common";
import { useMutation } from "@tanstack/react-query";
import { postLogout } from "api/user";
import { useNavigate } from "react-router-dom";
import { useSetIsLogin, useSetMember } from "store";

export default function Setting() {
  const navigate = useNavigate();
  const setMember = useSetMember();
  const setIsLogin = useSetIsLogin();

  // TODO: 개선 필요
  const { mutate: mutateLogout } = useMutation(postLogout, {
    onSuccess: () => {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");

      setIsLogin(false);
      setMember({
        id: -1,
        nickname: "",
        profileImgUrl: "",
      });
      // TODO: 로그아웃 시 전역 상태 초기화 필요 (현재 임시로 새로고침 되도록 설정)
      navigate(0);
    },
  });

  return (
    <Main>
      <UserAccount />
      <Button
        value="로그아웃"
        backgroundColor="accentPrimary"
        size={{ width: 330, height: 56 }}
        fontName="availableStrong16"
        color="accentText"
        radius={8}
        onClick={() => mutateLogout()}
      />
    </Main>
  );
}

import UserAccount from "@components/UserAccount";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { Main } from "@styles/common";
import { postLogout } from "api";
import { useSetAtom } from "jotai";
import { useNavigate } from "react-router-dom";
import { addressListAtom, memberAtom, tokensAtom, useTokens } from "store";

export default function Setting() {
  const navigate = useNavigate();
  const { refreshToken } = useTokens();

  const setMember = useSetAtom(memberAtom);
  const setTokens = useSetAtom(tokensAtom);
  const setAddresses = useSetAtom(addressListAtom);

  const onLogout = () => {
    refreshToken && postLogout({ refreshToken });

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("member");
    localStorage.removeItem("addresses");

    setMember(null);
    setTokens({
      accessToken: "",
      refreshToken: "",
    });
    setAddresses([]);

    navigate(ROUTE_PATH.account.index);
  };

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
        onClick={onLogout}
      />
    </Main>
  );
}

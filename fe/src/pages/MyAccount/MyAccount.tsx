import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Page } from "@styles/common";
import { useIsLoginValue } from "store";
import Login from "./Login";
import Setting from "./Setting";

export default function MyAccount() {
  const isLogin = useIsLoginValue();

  return (
    <Page>
      <TopBar
        title="내 계정"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      {isLogin && <Setting />}
      {!isLogin && <Login />}
      <NavigationBar />
    </Page>
  );
}

import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Page } from "@styles/common";

export default function Chat() {
  return (
    <Page>
      <TopBar
        title="채팅"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      Chat
      <NavigationBar />
    </Page>
  );
}

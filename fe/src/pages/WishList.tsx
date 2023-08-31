import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Page } from "@styles/common";

export default function WishList() {
  return (
    <Page>
      <TopBar
        title="관심 목록"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      WishList
      <NavigationBar />
    </Page>
  );
}

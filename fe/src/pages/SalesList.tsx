import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import { Page } from "@styles/common";

export default function SalesList() {
  return (
    <Page>
      <TopBar
        title="판매 내역"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
      />
      SalesList
      <NavigationBar />
    </Page>
  );
}

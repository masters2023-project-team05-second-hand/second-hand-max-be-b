import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { Page } from "@styles/common";

export default function Category() {
  return (
    <Page>
      <TopBar
        title="카테고리"
        backgroundColor="neutralBackgroundBlur"
        leftBtn={
          <Button
            value="뒤로"
            color="neutralText"
            fontName="availableStrong16"
            leftIcon={<ChevronLeftIcon />}
          />
        }
        isWithBorder={true}
      />
      Category
    </Page>
  );
}

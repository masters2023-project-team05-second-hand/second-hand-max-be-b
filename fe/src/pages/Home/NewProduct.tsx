import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { Page } from "@styles/common";

export default function NewProduct() {
  return (
    <Page>
      <TopBar
        title="내 물건 팔기"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
        leftBtn={
          <Button
            value="닫기" // Todo: 선택된 동네 상태로 변경
            color="neutralText"
            fontName="availableStrong16"
          />
        }
        rightBtn={
          <Button
            value="완료"
            fontName="availableStrong16"
            color="neutralText"
            disabled // Todo: 버튼 활성화 로직 필요
          />
        }
      />
      NewProduct
    </Page>
  );
}

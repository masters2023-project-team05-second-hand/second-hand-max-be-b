import { ReactComponent as ChevronDownIcon } from "@assets/icon/chevron-down.svg";
import { ReactComponent as LayoutGridIcon } from "@assets/icon/layout-grid.svg";
import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { Page } from "@styles/common";
import { styled } from "styled-components";

export default function Home() {
  return (
    <Page>
      <TopBar
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
        leftBtn={
          <Button
            value="역삼 1동" // Todo: 선택된 동네 상태로 변경
            color="neutralText"
            fontName="availableStrong16"
            leftIcon={<ChevronDownIcon />}
          />
        }
        rightBtn={<Button color="neutralText" leftIcon={<LayoutGridIcon />} />}
      />
      <Test>상품 목록 리스트</Test>
      <NavigationBar />
    </Page>
  );
}

const Test = styled.div`
  height: 2000px;
`;

import { ReactComponent as ChevronDownIcon } from "@assets/icon/chevron-down.svg";
import { ReactComponent as LayoutGridIcon } from "@assets/icon/layout-grid.svg";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import NavigationBar from "@components/NavigationBar";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { Page } from "@styles/common";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function Home() {
  const navigate = useNavigate();
  const moveToCategoryPage = () => navigate(ROUTE_PATH.category);
  const goToAddProduct = () => {
    navigate(ROUTE_PATH.new);
  };

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
        rightBtn={
          <Button
            color="neutralText"
            leftIcon={<LayoutGridIcon />}
            onClick={moveToCategoryPage}
          />
        }
      />
      <Test>상품 목록 리스트</Test>
      <FAB>
        <Button
          size={{ width: 56, height: 56 }}
          color="accentText"
          backgroundColor="accentPrimary"
          radius="half"
          leftIcon={<PlusIcon />}
          onClick={goToAddProduct}
        />
      </FAB>
      <NavigationBar />
    </Page>
  );
}

const Test = styled.div`
  height: 2000px;
`;

const FAB = styled.div`
  width: 56px;
  margin-left: auto;
  position: sticky;
  right: 24px;
  bottom: 24px;
`;
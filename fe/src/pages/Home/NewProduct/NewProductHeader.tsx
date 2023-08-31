import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { useNavigate } from "react-router-dom";

export default function NewProductHeader() {
  const navigate = useNavigate();

  const onClose = () => {
    navigate(-1);
  };

  const onAddNewProduct = () => {
    // Todo: 경로에 api response로 오는 상세 아이디 추가해야함
    navigate(ROUTE_PATH.product);
  };

  return (
    <TopBar
      title="내 물건 팔기"
      backgroundColor="neutralBackgroundBlur"
      isWithBorder={true}
      leftBtn={
        <Button
          value="닫기"
          color="neutralText"
          fontName="availableStrong16"
          onClick={onClose}
        />
      }
      rightBtn={
        <Button
          value="완료"
          fontName="availableStrong16"
          color="neutralText"
          disabled // Todo: 버튼 활성화 로직 필요
          onClick={onAddNewProduct}
        />
      }
    />
  );
}

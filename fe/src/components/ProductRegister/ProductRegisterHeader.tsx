import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { useNavigate } from "react-router-dom";
import { ProductInfo } from "./type";

type ProductRegisterHeaderProps = {
  productInfo: ProductInfo;
  onSubmit: () => void;
};

export default function ProductRegisterHeader({
  productInfo,
  onSubmit,
}: ProductRegisterHeaderProps) {
  const navigate = useNavigate();

  const onClose = () => {
    navigate(-1);
  };

  const isCoreFilled =
    !!productInfo.images.length && productInfo.title && productInfo.content;

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
          disabled={!isCoreFilled}
          onClick={onSubmit}
        />
      }
    />
  );
}

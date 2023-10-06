import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { useNavigate, useParams } from "react-router-dom";
import { ProductRegisterInfo } from "./type";
import {
  useMutateNewProduct,
  useMutatePatchProduct,
} from "@api/product/queries";
import { productKeys } from "@api/queryKeys";

type ProductRegisterHeaderProps = {
  productInfo: ProductRegisterInfo;
};

export default function ProductRegisterHeader({
  productInfo,
}: ProductRegisterHeaderProps) {
  const navigate = useNavigate();
  const { productId } = useParams();
  const numberProductId = Number(productId);
  const invalidateQueryKey = productKeys.detail(numberProductId).queryKey;

  const { mutate: mutateNewProduct } = useMutateNewProduct();
  const { mutate: mutatePatchProduct } = useMutatePatchProduct({
    productId: numberProductId,
    invalidateQueryKey,
  });

  const onPostNewProduct = () => {
    mutateNewProduct(productInfo);
  };

  const onPatchProduct = () => {
    mutatePatchProduct({
      productId: numberProductId,
      productInfo: productInfo,
    });
  };

  const onSubmitProduct = () => {
    const isNewProduct = !productId;

    if (isNewProduct) {
      onPostNewProduct();
    } else {
      onPatchProduct();
    }
  };

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
          onClick={onSubmitProduct}
        />
      }
    />
  );
}

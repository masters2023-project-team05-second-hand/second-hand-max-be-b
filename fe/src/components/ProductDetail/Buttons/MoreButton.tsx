import { useDeleteProductQuery } from "@api/product/queries";
import { ReactComponent as DotsIcon } from "@assets/icon/dots.svg";
import Alert from "@components/common/Alert/Alert";
import Button from "@components/common/Buttons/Button";
import MenuIndicator from "@components/common/Menu/MenuIndicator";
import { ROUTE_PATH } from "@router/constants";
import { useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";

export default function MoreButton() {
  const location = useLocation();
  const navigate = useNavigate();
  const { productId } = useParams();

  const goBack = () => navigate(location.state?.prevRoute ?? -1);
  const { onDeleteProduct } = useDeleteProductQuery({
    productId: productId!,
    onSuccess: goBack,
  });
  const [isRemoveAlertOpen, setIsRemoveAlertOpen] = useState(false);

  const openRemoveAlert = () => setIsRemoveAlertOpen(true);
  const closeRemoveAlert = () => setIsRemoveAlertOpen(false);

  const MoreMenuItemList = [
    {
      name: "게시글 수정",
      onClick: () => navigate(`${ROUTE_PATH.edit}/${productId}`),
    },
    { name: "삭제", onClick: openRemoveAlert, isWarning: true },
  ];

  return (
    <MenuIndicator itemList={MoreMenuItemList}>
      <Button color="accentText" leftIcon={<DotsIcon />} />
      {isRemoveAlertOpen && (
        <Alert
          message={"게시글을 삭제하시겠어요?"}
          onDeleteClick={onDeleteProduct}
          closeAlertHandler={closeRemoveAlert}
        />
      )}
    </MenuIndicator>
  );
}

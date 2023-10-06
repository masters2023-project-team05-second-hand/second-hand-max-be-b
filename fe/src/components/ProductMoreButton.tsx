import { ReactComponent as DotsIcon } from "@assets/icon/dots.svg";
import { useNavigate } from "react-router-dom";
import MenuIndicator from "./common/Menu/MenuIndicator";
import { MenuItemInfo } from "./common/Menu/type";
import { ROUTE_PATH } from "@router/constants";

type ProductMoreButtonProps = {
  productId: number;
  openDeleteAlert: () => void;
  statusListItems: MenuItemInfo[];
};

export default function ProductMoreButton({
  productId,
  statusListItems,
  openDeleteAlert,
}: ProductMoreButtonProps) {
  const navigate = useNavigate();

  const moreButtonItems = [
    {
      name: "게시글 수정",
      onClick: () => {
        navigate(`${ROUTE_PATH.edit}/${productId}`);
      },
    },
    ...statusListItems,
    {
      name: "삭제",
      onClick: () => {
        openDeleteAlert();
      },
      isWarning: true,
    },
  ];

  return (
    <MenuIndicator itemList={moreButtonItems}>
      <DotsIcon className="more" />
    </MenuIndicator>
  );
}

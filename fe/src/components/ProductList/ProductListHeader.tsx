import { ReactComponent as ChevronDownIcon } from "@assets/icon/chevron-down.svg";
import { ReactComponent as LayoutGridIcon } from "@assets/icon/layout-grid.svg";
import AddressModal from "@components/Modal/AddressModal/AddressModal";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import MenuIndicator from "@components/common/Menu/MenuIndicator";
import { ROUTE_PATH } from "@router/constants";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  useAddressListValue,
  useCurrentAddressId,
  useIsLoginValue,
} from "store";

export default function ProductListHeader() {
  const navigate = useNavigate();

  const isLogin = useIsLoginValue();
  const addressList = useAddressListValue();
  const [currentAddressId, setCurrentAddressId] = useCurrentAddressId();
  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);

  const openAddressModal = () => setIsAddressModalOpen(true);
  const closeAddressModal = () => setIsAddressModalOpen(false);

  const moveToCategoryPage = () => {
    navigate(ROUTE_PATH.category);
  };

  const selectedAddress = addressList.find(
    (address) => address.id === currentAddressId
  );

  const addressMenuList = addressList.map((address) => {
    const isSelected = address.id === selectedAddress?.id;

    return {
      id: address.id,
      name: address.name,
      onClick: () => {
        setCurrentAddressId(address.id);
      },
      isSelected: isSelected,
    };
  });

  const addressMenuItems = isLogin
    ? [
        ...addressMenuList,
        {
          name: "내 동네 설정하기",
          onClick: () => {
            openAddressModal();
          },
        },
      ]
    : addressMenuList;

  return (
    <>
      <TopBar
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
        leftBtn={
          <MenuIndicator itemList={addressMenuItems}>
            <Button
              value={selectedAddress?.name}
              color="neutralText"
              fontName="availableStrong16"
              leftIcon={<ChevronDownIcon />}
            />
          </MenuIndicator>
        }
        rightBtn={
          <Button
            color="neutralText"
            leftIcon={<LayoutGridIcon />}
            onClick={moveToCategoryPage}
          />
        }
      />
      {isAddressModalOpen && <AddressModal closeHandler={closeAddressModal} />}
    </>
  );
}

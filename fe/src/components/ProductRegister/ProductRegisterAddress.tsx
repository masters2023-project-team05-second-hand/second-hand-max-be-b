import { ReactComponent as MapIcon } from "@assets/icon/map-pin-filled.svg";
import { HEIGHT, WIDTH } from "@styles/constants";
import { styled } from "styled-components";
import { useState } from "react";
import Menu from "@components/common/Menu/Menu";
import { AddressInfo } from "@api/type";
import { useAddressListValue } from "store";
import { PLACE_HOLDER } from "./constants";

type ProductRegisterAddressProps = {
  selectedAddressId?: number;
  address?: AddressInfo;
  onChange: (addressList: AddressInfo[], newAddressId: number) => void;
};

export default function ProductRegisterAddress({
  selectedAddressId,
  address,
  onChange,
}: ProductRegisterAddressProps) {
  const addressList = useAddressListValue();
  const [isAddressMenuOpen, setIsAddressMenuOpen] = useState(false);

  const addressMenuList = addressList.map((address) => ({
    id: address.id,
    name: address.name,
    onClick: () => {
      onChange(addressList, address.id);
      toggleAddressMenu();
    },
    isSelected: address.id === selectedAddressId,
  }));

  const toggleAddressMenu = () => {
    setIsAddressMenuOpen((prev) => !prev);
  };

  return (
    <Address>
      <AddressButton onClick={toggleAddressMenu}>
        <MapIcon />
        <span>{address?.name ?? PLACE_HOLDER.ADDRESS}</span>
      </AddressButton>
      {isAddressMenuOpen && (
        <Menu
          itemList={addressMenuList}
          withShadow={true}
          positionX="left"
          positionY="bottom"
          closeMenuHandler={toggleAddressMenu}
        />
      )}
    </Address>
  );
}

const Address = styled.div`
  position: fixed;
  bottom: 0;
`;

const AddressButton = styled.button`
  display: flex;
  align-items: center;
  gap: 8px;
  position: fixed;
  bottom: 0;
  padding: 0 16px;
  box-sizing: border-box;
  width: ${WIDTH.app}px;
  height: ${HEIGHT.navigationBar}px;
  border-top: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundWeak};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;

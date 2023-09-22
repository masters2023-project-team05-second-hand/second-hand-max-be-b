import { AddressInfo } from "@api/type";
import { ReactComponent as MapIcon } from "@assets/icon/map-pin-filled.svg";
import MenuIndicator from "@components/common/Menu/MenuIndicator";
import { BottomBar } from "@styles/common";
import { useAddressListValue } from "store";
import { styled } from "styled-components";
import { PLACE_HOLDER } from "./constants";
import { getFormattedAddress } from "@utils/index";

type ProductRegisterAddressProps = {
  selectedAddressId: number | null;
  address?: AddressInfo;
  onChange: (addressList: AddressInfo[], newAddressId: number) => void;
};

export default function ProductRegisterAddress({
  selectedAddressId,
  address,
  onChange,
}: ProductRegisterAddressProps) {
  const addressList = useAddressListValue();

  const addressMenuList = addressList.map((address) => ({
    id: address.id,
    name: getFormattedAddress(address.name),
    onClick: () => {
      onChange(addressList, address.id);
    },
    isSelected: address.id === selectedAddressId,
  }));

  return (
    <BottomBar>
      <MenuIndicator itemList={addressMenuList} withShadow={true}>
        <AddressButton>
          <MapIcon />
          <span>
            {getFormattedAddress(address?.name) ?? PLACE_HOLDER.address}
          </span>
        </AddressButton>
      </MenuIndicator>
    </BottomBar>
  );
}

const AddressButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 8px;
  width: 100%;
  height: 100%;
  padding: 0 16px;
  font: inherit;

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;

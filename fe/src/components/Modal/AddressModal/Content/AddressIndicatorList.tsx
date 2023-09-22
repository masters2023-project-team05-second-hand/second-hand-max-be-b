import { AddressInfo } from "api/type";
import { useState } from "react";
import styled from "styled-components";
import AddressRemoveAlert from "../AddressRemoveAlert";
import { AddressAddButton, AddressRemoveButton } from "../Buttons";
import { getFormattedAddress } from "@utils/index";

export default function AddressIndicatorList({
  currentAddresses,
  currentSelectedAddressId,
  changeCurrentAddresses,
  changeCurrentAddressId,
  openAddressSearch,
}: {
  currentAddresses: AddressInfo[];
  currentSelectedAddressId: number;
  changeCurrentAddresses: (newAddresses: AddressInfo[]) => void;
  changeCurrentAddressId: (newAddressId: number) => void;
  openAddressSearch: () => void;
}) {
  const [isRemoveAlertOpen, setIsRemoveAlertOpen] = useState(false);
  const [removeTargetAddress, setRemoveTargetAddress] =
    useState<AddressInfo | null>(null);

  const openRemoveAlert = () => setIsRemoveAlertOpen(true);
  const closeRemoveAlert = () => setIsRemoveAlertOpen(false);

  const isOnlyOneAddress = currentAddresses.length === 1;
  const isFullAddress = currentAddresses.length === 2;

  const onRemoveClick = (address: AddressInfo) => {
    setRemoveTargetAddress(address);
    openRemoveAlert();
  };

  const onRemoveAddress = () => {
    const newAddresses = currentAddresses.filter(
      ({ id }) => id !== removeTargetAddress?.id
    );
    changeCurrentAddresses(newAddresses);
    changeCurrentAddressId(newAddresses[0].id);
  };

  return (
    <StyledAddressIndicatorList>
      <NoticeText>
        <span>지역은 최소 1개,</span>
        <span>최대 2개까지 설정 가능해요.</span>
      </NoticeText>
      <div className="button-wrapper">
        {currentAddresses.map((address) => (
          <AddressIndicator
            key={address.id}
            $active={address.id === currentSelectedAddressId}
            onClick={() => changeCurrentAddressId(address.id)}>
            <span>{getFormattedAddress(address.name)}</span>
            <AddressRemoveButton {...{ address, onRemoveClick }} />
          </AddressIndicator>
        ))}
        <AddressAddButton
          disabled={isFullAddress}
          onClick={openAddressSearch}
        />
      </div>
      {isRemoveAlertOpen && removeTargetAddress && (
        <AddressRemoveAlert
          {...{
            isOnlyOneAddress,
            targetAddressName: removeTargetAddress.name,
            onConfirmClick: openAddressSearch,
            onDeleteClick: onRemoveAddress,
            closeAlertHandler: closeRemoveAlert,
          }}
        />
      )}
    </StyledAddressIndicatorList>
  );
}

const StyledAddressIndicatorList = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
  height: 600px;

  .button-wrapper {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }
`;

const AddressIndicator = styled.div<{ $active: boolean }>`
  display: flex;
  justify-content: space-between;
  padding: 0 16px;
  align-items: center;
  height: 56px;
  cursor: pointer;

  background-color: ${({ $active, theme: { color } }) =>
    $active ? color.accentPrimary : color.neutralOverlay};
  border-radius: ${({ theme: { radius } }) => radius[8]};
  font: ${({ theme: { font } }) => font.availableStrong16};
  color: ${({ theme: { color } }) => color.accentText};
`;

const NoticeText = styled.div`
  display: flex;
  flex-direction: column;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralText};
  padding-top: 48px;

  span {
    display: flex;
    justify-content: center;
  }
`;

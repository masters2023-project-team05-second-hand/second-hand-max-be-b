import { ReactComponent as CircleXIcon } from "@assets/icon/circle-x-filled.svg";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import Alert from "@components/common/Alert/Alert";
import Button from "@components/common/Buttons/Button";
import { AddressInfo } from "api/type";
import { useState } from "react";
import { useAddressList, useCurrentAddressId } from "store";
import styled from "styled-components";

export default function AddressIndicatorList({
  openAddressSearch,
}: {
  openAddressSearch: () => void;
}) {
  const [addresses, setAddresses] = useAddressList();
  const [currentAddressId, setCurrentAddressId] = useCurrentAddressId();

  const [isRemoveAlertOpen, setIsRemoveAlertOpen] = useState(false);
  const [removeTargetAddress, setRemoveTargetAddress] =
    useState<AddressInfo | null>(null);

  const openRemoveAlert = () => setIsRemoveAlertOpen(true);
  const closeRemoveAlert = () => setIsRemoveAlertOpen(false);

  const isOnlyOneAddress = addresses.length === 1;
  const alertMessage = isOnlyOneAddress
    ? "동네는 최소 1개 이상 선택해야 해요. 동네를 다시 선택하시겠어요?"
    : `${removeTargetAddress?.name}을 삭제하시겠어요?`;

  const onRemoveClick = (address: AddressInfo) => {
    setRemoveTargetAddress(address);
    openRemoveAlert();
  };

  const onRemoveAddress = () => {
    const newAddresses = addresses.filter(
      ({ id }) => id !== removeTargetAddress?.id
    );
    setAddresses(newAddresses);
    setCurrentAddressId(newAddresses[0].id);
  };

  return (
    <StyledAddressIndicatorList>
      <NoticeText>
        <span>지역은 최소 1개,</span>
        <span>최대 2개까지 설정 가능해요.</span>
      </NoticeText>
      <div className="button-wrapper">
        {addresses.map(({ id, name }) => (
          <AddressIndicator
            key={id}
            $active={id === currentAddressId}
            onClick={() => setCurrentAddressId(id)}>
            <span>{name}</span>
            <Button
              leftIcon={<CircleXIcon />}
              color="accentText"
              onClick={(e) => {
                e.stopPropagation();
                onRemoveClick({ id, name });
              }}
            />
          </AddressIndicator>
        ))}
        <Button
          size={{ width: 288, height: 56 }}
          leftIcon={<PlusIcon />}
          fontName="availableStrong16"
          color="accentTextWeak"
          borderColor="neutralBorder"
          radius={8}
          value="추가"
          onClick={openAddressSearch}
        />
      </div>
      {isRemoveAlertOpen && !isOnlyOneAddress && (
        <Alert
          message={alertMessage}
          onDeleteClick={onRemoveAddress}
          closeAlertHandler={closeRemoveAlert}
        />
      )}
      {isRemoveAlertOpen && isOnlyOneAddress && (
        <Alert
          message={alertMessage}
          onConfirmClick={openAddressSearch}
          closeAlertHandler={closeRemoveAlert}
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

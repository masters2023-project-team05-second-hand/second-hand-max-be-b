import { ReactComponent as CircleXIcon } from "@assets/icon/circle-x-filled.svg";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import Button from "@components/common/Buttons/Button";
import { TextWeak } from "@styles/common";
import { AddressInfo } from "api/type";
import { MouseEventHandler } from "react";

export function AddressAddButton({
  disabled,
  onClick,
}: {
  disabled: boolean;
  onClick: () => void;
}) {
  return (
    <>
      <Button
        size={{ width: 288, height: 56 }}
        leftIcon={<PlusIcon />}
        fontName="availableStrong16"
        color="accentTextWeak"
        borderColor="neutralBorder"
        radius={8}
        value="추가"
        disabled={disabled}
        onClick={onClick}
      />
      {disabled && (
        <TextWeak>
          더 이상 동네를 추가할 수 없어요. 새로운 동네를 추가하고 싶다면 먼저
          동네를 삭제해주세요!
        </TextWeak>
      )}
    </>
  );
}

export function AddressRemoveButton({
  address,
  onRemoveClick,
}: {
  address: AddressInfo;
  onRemoveClick: (address: AddressInfo) => void;
}) {
  const AddressRemoveHandler: MouseEventHandler = (e) => {
    e.stopPropagation();
    onRemoveClick(address);
  };

  return (
    <Button
      leftIcon={<CircleXIcon />}
      color="accentText"
      onClick={AddressRemoveHandler}
    />
  );
}

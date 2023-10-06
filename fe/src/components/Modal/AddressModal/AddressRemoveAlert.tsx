import Alert from "@components/common/Alert/Alert";

export default function AddressRemoveAlert({
  isOnlyOneAddress,
  targetAddressName,
  onConfirmClick,
  onDeleteClick,
  closeAlertHandler,
}: {
  isOnlyOneAddress: boolean;
  targetAddressName: string;
  onConfirmClick: () => void;
  onDeleteClick: () => void;
  closeAlertHandler: () => void;
}) {
  const removeAlertMessage = isOnlyOneAddress
    ? "동네는 최소 1개 이상 선택해야 해요. 동네를 다시 선택하시겠어요?"
    : `${targetAddressName}을 삭제하시겠어요?`;

  return (
    <Alert
      message={removeAlertMessage}
      closeAlertHandler={closeAlertHandler}
      onDeleteClick={!isOnlyOneAddress ? onDeleteClick : undefined}
      onConfirmClick={isOnlyOneAddress ? onConfirmClick : undefined}
    />
  );
}

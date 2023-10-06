import { useStatusesValue } from "store";
import { RESERVATION_ID, SELLING_ID } from "store/constants";
import { styled } from "styled-components";

type ProductStatusProps = {
  id: number;
};

export default function ProductStatus({ id }: ProductStatusProps) {
  const productStatuses = useStatusesValue();

  const statusType = productStatuses?.find((status) => status.id === id)?.type;
  const isSelling = id === SELLING_ID;

  return (
    <>
      {!isSelling && statusType && <Status $statusId={id}>{statusType}</Status>}
    </>
  );
}

const Status = styled.div<{ $statusId: number }>`
  box-sizing: border-box;
  padding: 3px 6px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  background-color: ${({ theme: { color }, $statusId }) =>
    $statusId === RESERVATION_ID
      ? color.accentSecondary
      : color.neutralBorderStrong};
  color: ${({ theme: { color } }) => color.accentText};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

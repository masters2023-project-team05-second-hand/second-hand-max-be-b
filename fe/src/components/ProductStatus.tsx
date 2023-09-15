import { useProductStatusesQuery } from "@api/product/queries";
import { IS_RESERVATION_ID, IS_SELLING_ID } from "store/constants";
import { styled } from "styled-components";

type ProductStatusProps = {
  id: number;
};

export default function ProductStatus({ id }: ProductStatusProps) {
  const { data, isSuccess } = useProductStatusesQuery();

  const statusName = isSuccess && data.find((item) => item.id === id)?.type;
  const isSelling = id === IS_SELLING_ID;

  return <>{!isSelling && <Status $statusId={id}>{statusName}</Status>}</>;
}

const Status = styled.div<{ $statusId: number }>`
  box-sizing: border-box;
  padding: 3px 6px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  background-color: ${({ theme: { color }, $statusId }) =>
    $statusId === IS_RESERVATION_ID
      ? color.accentSecondary
      : color.neutralBorderStrong};
  color: ${({ theme: { color } }) => color.accentText};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

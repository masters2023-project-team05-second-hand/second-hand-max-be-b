import { useMutateProductStatus } from "@api/product/queries";
import { productKeys } from "@api/queryKeys";
import { Status } from "@api/type";
import { useParams } from "react-router-dom";
import { ListItem, ListPanel } from "../Modal.style";

export default function ProductStatusContent({
  productStatuses,
  closeHandler,
}: {
  productStatuses: Status[];
  closeHandler: () => void;
}) {
  const { productId } = useParams();
  const numberProductId = Number(productId);

  const { mutate: mutateProductStatus } = useMutateProductStatus({
    onSettled: closeHandler,
    invalidateQueryKey: productKeys.detail(numberProductId).queryKey,
  });

  return (
    <ListPanel>
      {productStatuses.map(({ id, type }) => (
        <ListItem
          key={id}
          $active={false}
          onClick={() =>
            mutateProductStatus({ productId: numberProductId, statusId: id })
          }>
          <span>{type}</span>
        </ListItem>
      ))}
    </ListPanel>
  );
}

import { useMutateProductStatus } from "@api/product/queries";
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

  const { mutate: mutateProductStatus } = useMutateProductStatus({
    onSettled: closeHandler,
    invalidateQueryKey: ["getProductDetail", productId!],
  });

  return (
    <ListPanel>
      {productStatuses.map(({ id, type }) => (
        <ListItem
          key={id}
          $active={false}
          onClick={() =>
            mutateProductStatus({ productId: productId, statusId: id })
          }>
          <span>{type}</span>
        </ListItem>
      ))}
    </ListPanel>
  );
}

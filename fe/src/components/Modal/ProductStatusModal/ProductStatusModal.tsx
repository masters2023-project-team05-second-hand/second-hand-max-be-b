import { Status } from "@api/type";
import useOutsideClick from "@hooks/useOutsideClick";
import Modal from "../Modal";
import ProductStatusContent from "./ProductStatusContent";

export default function ProductStatusModal({
  productStatuses,
  closeHandler,
}: {
  productStatuses: Status[];
  closeHandler: () => void;
}) {
  const { ref: productStatusModalRef } =
    useOutsideClick<HTMLDivElement>(closeHandler);

  return (
    <Modal
      ref={productStatusModalRef}
      header={{
        title: "상태 변경",
        closeHandler,
      }}
      content={<ProductStatusContent {...{ productStatuses, closeHandler }} />}
    />
  );
}

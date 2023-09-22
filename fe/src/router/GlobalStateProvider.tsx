import { useProductStatusesQuery } from "@api/product/queries";
import { useToast } from "@hooks/useToast";
import { useEffect } from "react";
import { useSetStatuses } from "store";

export default function GlobalStateProvider() {
  const setStatuses = useSetStatuses();

  const { toast } = useToast();

  const {
    data: productStatuses,
    isSuccess,
    isError,
  } = useProductStatusesQuery();

  useEffect(() => {
    if (isSuccess) {
      setStatuses(productStatuses);
    }
    if (isError) {
      toast({
        type: "error",
        title: "상품 상태 정보 조회 실패",
        message: "상품 상태 정보를 조회하는데 실패했습니다. 새로고침 해주세요.",
      });
    }
  }, [isSuccess, isError, productStatuses, setStatuses, toast]);

  return null;
}

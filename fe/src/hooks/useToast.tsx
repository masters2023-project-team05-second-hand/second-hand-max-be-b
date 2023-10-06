import { useSetAtom } from "jotai";
import { useCallback } from "react";
import { ToasterInfo, useToasterAtom } from "store/toaster";

export const useToast = () => {
  const setToaster = useSetAtom(useToasterAtom);

  const toast = useCallback(
    (toasterInfo: ToasterInfo) =>
      setToaster({ type: "add", payload: { ...toasterInfo } }),
    [setToaster]
  );

  return {
    toast,
  };
};

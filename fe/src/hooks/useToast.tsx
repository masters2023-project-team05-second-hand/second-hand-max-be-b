import { useSetAtom } from "jotai";
import { ToasterInfo, useToasterAtom } from "store/toaster";

export const useToast = () => {
  const setToaster = useSetAtom(useToasterAtom);

  return {
    toast: (toasterInfo: ToasterInfo) =>
      setToaster({ type: "add", payload: { ...toasterInfo } }),
  };
};

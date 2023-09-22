import { atom } from "jotai";

export type ToasterInfo = {
  title: string;
  message: string;
  type: "success" | "error" | "info";
};

export type Toaster = {
  id: number;
} & ToasterInfo;

type ToasterAtom = {
  toasts: Toaster[];
  index: number;
};

const toasterAtom = atom<ToasterAtom>({
  toasts: [],
  index: 0,
});

type ToasterAction =
  | {
      type: "add";
      payload: ToasterInfo;
    }
  | {
      type: "remove";
      payload: { id: number };
    };

export const useToasterAtom = atom(
  (get) => get(toasterAtom).toasts,
  (get, set, { type, payload }: ToasterAction) => {
    const prevAtom = get(toasterAtom);

    switch (type) {
      case "add":
        set(toasterAtom, {
          toasts: [...prevAtom.toasts, { ...payload, id: prevAtom.index + 1 }],
          index: prevAtom.index + 1,
        });
        break;
      case "remove":
        set(toasterAtom, {
          toasts: prevAtom.toasts.filter((toast) => toast.id !== payload.id),
          index: prevAtom.index + 1,
        });
        break;
    }
  }
);

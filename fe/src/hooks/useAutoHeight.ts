import { MutableRefObject, useRef } from "react";

type AutoHeightReturnType<T extends HTMLElement> = {
  ref: MutableRefObject<T | null>;
  onChange: (text: string) => void;
};

export default function useAutoHeight<T extends HTMLElement>(
  changeHandler: (text: string) => void
): AutoHeightReturnType<T> {
  const ref = useRef<T>(null);

  const onChange = (text: string) => {
    changeHandler(text);

    if (ref.current) {
      ref.current.style.height = "auto";
      ref.current.style.height = `${ref.current.scrollHeight}px`;
    }
  };

  return {
    ref,
    onChange,
  };
}

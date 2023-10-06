import { MutableRefObject, useRef } from "react";

type AutoHeightReturnType<T extends HTMLElement> = {
  ref: MutableRefObject<T | null>;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
};

export default function useAutoHeight<T extends HTMLElement>(
  changeHandler: (e: React.ChangeEvent<HTMLTextAreaElement>) => void
): AutoHeightReturnType<T> {
  const ref = useRef<T>(null);

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    changeHandler(e);

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

import { MutableRefObject, useEffect, useRef, useState } from "react";

type ReturnType<T extends HTMLElement> = {
  ref: MutableRefObject<T | null>;
};

export default function useOutsideClick<T extends HTMLElement>(
  closeHandler: () => void
): ReturnType<T> {
  const ref = useRef<T | null>(null);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    setIsOpen(true);

    const handleOutsideClick = (e: MouseEvent) => {
      if (!ref.current?.contains(e.target as Node)) {
        closeHandler();
      }
    };

    if (isOpen) {
      window.addEventListener("click", handleOutsideClick);
    }

    return () => {
      window.removeEventListener("click", handleOutsideClick);
    };
  }, [isOpen, closeHandler]);

  return { ref };
}

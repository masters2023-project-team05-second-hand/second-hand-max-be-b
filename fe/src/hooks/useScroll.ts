import { MutableRefObject, useEffect, useRef, useState } from "react";

type ScrollReturnType = {
  scrollY: number | undefined;
  ref: MutableRefObject<HTMLDivElement | null>;
};

export default function useScroll(): ScrollReturnType {
  const [scrollY, setScrollY] = useState<number>(0);
  const ref = useRef<HTMLDivElement>(null);

  const onScroll = () => {
    setScrollY(ref.current?.scrollTop ?? 0);
  };

  useEffect(() => {
    const watchScroll = () => {
      ref.current?.addEventListener("scroll", onScroll);
    };

    watchScroll();
  });

  return {
    scrollY,
    ref,
  };
}

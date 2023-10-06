import { useRef, useEffect, MutableRefObject } from "react";

type ReturnType<T extends HTMLElement> = {
  scrollRef: MutableRefObject<T | null>;
};

export default function useSideScroll<T extends HTMLElement>(): ReturnType<T> {
  const scrollRef = useRef<T | null>(null);

  useEffect(() => {
    const element = scrollRef.current;

    if (!element) return;

    const onWheel = (e: WheelEvent) => {
      e.preventDefault();

      const isScrollable = e.deltaY !== 0;
      if (!isScrollable) return;

      element.scrollTo({
        left: element.scrollLeft + e.deltaY,
        behavior: "smooth",
      });
    };
    element.addEventListener("wheel", onWheel);

    return () => {
      element.removeEventListener("wheel", onWheel);
    };
  }, []);

  return { scrollRef };
}

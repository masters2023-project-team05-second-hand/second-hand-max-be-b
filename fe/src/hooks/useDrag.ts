import { MutableRefObject, useRef, useState } from "react";

type ReturnType<T extends HTMLElement> = {
  ref: MutableRefObject<T | null>;
  onDragStart: (e: React.MouseEvent<T>) => void;
  onDragEnd: () => void;
  onDragMove: (e: React.MouseEvent<T>) => void;
};

export default function useDrag<T extends HTMLElement>(): ReturnType<T> {
  const ref = useRef<T | null>(null);
  const [isDrag, setIsDrag] = useState(false);
  const [startX, setStartX] = useState<number>(0);

  const onDragStart = (e: React.MouseEvent<T>) => {
    e.preventDefault();
    setIsDrag(true);
    setStartX(e.pageX + ref.current!.scrollLeft);
  };

  const onDragEnd = () => {
    setIsDrag(false);
  };

  const onDragMove = (e: React.MouseEvent<T>) => {
    if (isDrag) {
      ref.current!.scrollLeft = startX - e.pageX;
    }
  };

  return { ref, onDragStart, onDragEnd, onDragMove };
}

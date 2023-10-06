import { useState } from "react";

type AnimationReturnType = {
  isAnimating: boolean;
  onLeavePage: () => void;
};

export default function useAnimation(): AnimationReturnType {
  const [isAnimating, setIsAnimating] = useState(true);

  const onLeavePage = () => {
    setIsAnimating(false);
  };

  return { isAnimating, onLeavePage };
}

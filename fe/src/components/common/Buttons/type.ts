import { ColorName, FontName, Radius } from "@styles/designSystem";
import React from "react";

export type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  size?: { width: number; height: number };
  radius?: Radius;
  fontName?: FontName;
  color?: ColorName;
  backgroundColor?: ColorName;
  borderColor?: ColorName;
  leftIcon?: React.ReactElement;
  rightIcon?: React.ReactElement;
};

export type ButtonContentProps = Pick<
  ButtonProps,
  "leftIcon" | "rightIcon" | "children" | "value"
>;

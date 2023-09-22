import { HTMLAttributes } from "react";

export type MenuItemInfo = {
  id?: number;
  name: string;
  onClick: (id?: number) => void;
  isSelected?: boolean;
  isWarning?: boolean;
};

export type MenuProps = {
  itemList: MenuItemInfo[];
  withShadow?: boolean;
};

export type MenuIndicatorProps = MenuProps & HTMLAttributes<HTMLDivElement>;

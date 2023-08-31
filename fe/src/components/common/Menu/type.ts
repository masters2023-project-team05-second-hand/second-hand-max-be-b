export type MenuItemInfo = {
  value: string;
  onClick: (id?: number) => void;
  itemId?: number;
  isSelected?: boolean;
  isWarning?: boolean;
};

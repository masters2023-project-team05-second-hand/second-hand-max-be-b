export type MenuItemInfo = {
  id?: number;
  name: string;
  onClick: (id?: number) => void;
  isSelected?: boolean;
  isWarning?: boolean;
};

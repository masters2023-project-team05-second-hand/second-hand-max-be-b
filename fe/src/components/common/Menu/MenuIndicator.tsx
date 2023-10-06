import useMenuPosition from "@hooks/useMenuPosition";
import { useState } from "react";
import Menu from "./Menu";
import { MenuIndicatorProps } from "./type";

export default function MenuIndicator({
  itemList,
  withShadow,
  ...props
}: MenuIndicatorProps) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { ref, position, calcPosition } = useMenuPosition();

  const toggleModal = () => {
    setIsMenuOpen((prev) => !prev);
  };

  const onClickMenuIndicator = (e: React.MouseEvent) => {
    e.stopPropagation();
    calcPosition(e);
    toggleModal();
  };

  return (
    <div ref={ref} onClick={onClickMenuIndicator}>
      {props.children}
      {isMenuOpen && (
        <Menu
          itemList={itemList}
          withShadow={withShadow}
          position={position}
          closeMenuHandler={toggleModal}
        />
      )}
    </div>
  );
}

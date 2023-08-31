import { ReactNode, useState } from "react";
import { styled } from "styled-components";
import Menu from "./Menu";
import { MenuItemInfo } from "./type";

type MenuIndicatorProps = {
  button: ReactNode;
  itemList: MenuItemInfo[];
  withShadow?: boolean;
  position?: "left" | "right";
};

export default function MenuIndicator({
  button,
  itemList,
  withShadow,
  position,
}: MenuIndicatorProps) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleModal = () => {
    setIsMenuOpen((prev) => !prev);
  };

  return (
    <StyledMenuIndicator onClick={toggleModal}>
      {button}
      {isMenuOpen && (
        <Menu
          itemList={itemList}
          withShadow={withShadow}
          position={position}
          closeMenuHandler={toggleModal}
        />
      )}
    </StyledMenuIndicator>
  );
}

const StyledMenuIndicator = styled.div`
  position: relative;
`;

import { HTMLAttributes, ReactNode, useState } from "react";
import { styled } from "styled-components";
import Menu from "./Menu";
import { MenuItemInfo } from "./type";

type MenuIndicatorProps = {
  button: ReactNode;
  itemList: MenuItemInfo[];
  withShadow?: boolean;
  positionX?: "left" | "right";
  positionY?: "top" | "bottom";
} & HTMLAttributes<HTMLDivElement>;

export default function MenuIndicator({
  button,
  itemList,
  withShadow,
  positionX,
  positionY,
  ...props
}: MenuIndicatorProps) {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleModal = () => {
    setIsMenuOpen((prev) => !prev);
  };

  return (
    <StyledMenuIndicator id={props.id} onClick={toggleModal}>
      {button}
      {isMenuOpen && (
        <Menu
          itemList={itemList}
          withShadow={withShadow}
          positionX={positionX}
          positionY={positionY}
          closeMenuHandler={toggleModal}
        />
      )}
    </StyledMenuIndicator>
  );
}

const StyledMenuIndicator = styled.div`
  position: relative;
`;

import useOutsideClick from "@hooks/useOutsideClick";
import { styled } from "styled-components";
import MenuItem from "./MenuItem";
import { MenuItemInfo } from "./type";

type MenuProps = {
  itemList: MenuItemInfo[];
  withShadow?: boolean;
  positionX?: "left" | "right";
  positionY?: "top" | "bottom";
  closeMenuHandler: () => void;
};

export default function Menu({
  itemList,
  withShadow,
  positionX,
  positionY,
  closeMenuHandler,
}: MenuProps) {
  const { ref: menuRef } = useOutsideClick<HTMLDivElement>(closeMenuHandler);

  return (
    <StyledMenu
      $withShadow={withShadow}
      $positionX={positionX}
      $positionY={positionY}
      ref={menuRef}>
      <ul>
        {itemList.map((item) => (
          <MenuItem key={item.id} {...item} />
        ))}
      </ul>
    </StyledMenu>
  );
}

const StyledMenu = styled.div<{
  $withShadow?: boolean;
  $positionX?: "left" | "right";
  $positionY?: "top" | "bottom";
}>`
  position: absolute;
  width: 240px;
  overflow: hidden;
  z-index: 2;
  ${({ $positionX }) => ($positionX === "left" ? `left: 16px` : "right: 16px")};
  ${({ $positionY }) => ($positionY === "top" ? `top: 56px` : "bottom: 56px")};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  border: ${({ theme: { color } }) => `0.8px solid ${color.neutralBorder}`};
  border-radius: 12px;
  box-shadow: ${({ $withShadow }) =>
    $withShadow ? "0px 4px 4px 0px #00000040" : "none"};
`;

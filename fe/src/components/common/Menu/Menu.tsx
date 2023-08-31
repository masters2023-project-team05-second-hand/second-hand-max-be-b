import useOutsideClick from "@hooks/useOutsideClick";
import { styled } from "styled-components";
import MenuItem from "./MenuItem";
import { MenuItemInfo } from "./type";

type MenuProps = {
  itemList: MenuItemInfo[];
  withShadow?: boolean;
  position?: "left" | "right";
  closeMenuHandler: () => void;
};

export default function Menu({
  itemList,
  withShadow,
  position,
  closeMenuHandler,
}: MenuProps) {
  const { ref: menuRef } = useOutsideClick<HTMLDivElement>(closeMenuHandler);

  return (
    <StyledMenu $withShadow={withShadow} $position={position} ref={menuRef}>
      <ul>
        {itemList.map((item) => (
          <MenuItem key={item.itemId} {...item} />
        ))}
      </ul>
    </StyledMenu>
  );
}

const StyledMenu = styled.div<{
  $withShadow?: boolean;
  $position?: "left" | "right";
}>`
  position: absolute;
  width: 240px;
  top: 56px;
  overflow: hidden;
  z-index: 2;
  ${({ $position }) => ($position === "left" ? `left: 16px` : "right: 16px")};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  border: ${({ theme: { color } }) => `0.8px solid ${color.neutralBorder}`};
  border-radius: 12px;
  box-shadow: ${({ $withShadow }) =>
    $withShadow ? "0px 4px 4px 0px #00000040" : "none"};
`;

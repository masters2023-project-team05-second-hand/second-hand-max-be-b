import useOutsideClick from "@hooks/useOutsideClick";
import { styled } from "styled-components";
import MenuItem from "./MenuItem";
import { MenuProps } from "./type";
import { createPortal } from "react-dom";
import { Dim } from "@styles/common";
import { useId } from "react";

export default function Menu({
  itemList,
  withShadow,
  position,
  closeMenuHandler,
}: MenuProps & {
  position: {
    left?: number | null;
    right?: number | null;
    top?: number | null;
    bottom?: number | null;
  };
  closeMenuHandler: () => void;
}) {
  const { ref: menuRef } = useOutsideClick<HTMLDivElement>(closeMenuHandler);
  const id = useId();

  return createPortal(
    <Dim>
      <StyledMenu $withShadow={withShadow} $position={position} ref={menuRef}>
        <ul>
          {itemList.map((item) => (
            <MenuItem key={id + item.name} {...item} />
          ))}
        </ul>
      </StyledMenu>
    </Dim>,
    document.getElementById("modal-root")!
  );
}

const StyledMenu = styled.div<{
  $withShadow?: boolean;
  $position?: {
    left?: number | null;
    right?: number | null;
    top?: number | null;
    bottom?: number | null;
  };
}>`
  position: fixed;
  width: 240px;
  overflow: hidden;
  z-index: 2;
  left: ${({ $position }) => $position && $position.left}px;
  right: ${({ $position }) => $position && $position.right}px;
  top: ${({ $position }) => $position && $position.top}px;
  bottom: ${({ $position }) => $position && $position.bottom}px;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  border: ${({ theme: { color } }) => `0.8px solid ${color.neutralBorder}`};
  border-radius: 12px;
  box-shadow: ${({ $withShadow }) =>
    $withShadow ? "0px 4px 4px 0px #00000040" : "none"};
`;

import { styled } from "styled-components";
import { MenuItemInfo } from "./type";

export default function MenuItem({
  value,
  onClick,
  itemId,
  isSelected,
  isWarning,
}: MenuItemInfo) {
  return (
    <StyledItem
      $isSelected={isSelected}
      $isWarning={isWarning}
      onClick={() => onClick(itemId)}>
      <span>{value}</span>
    </StyledItem>
  );
}

const StyledItem = styled.li<{
  $isSelected?: boolean;
  $isWarning?: boolean;
}>`
  background-color: inherit;
  padding: 16px;
  cursor: pointer;
  color: ${({ $isWarning, theme: { color } }) =>
    $isWarning ? color.systemWarning : color.neutralTextStrong};
  font: ${({ $isSelected, theme: { font } }) =>
    $isSelected ? font.displayStrong16 : font.availableDefault16};
  border-bottom: ${({ theme: { color } }) =>
    `0.8px solid ${color.neutralBorder}`};

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }
`;

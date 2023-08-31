import { ColorName, FontName, Radius } from "@styles/designSystem";
import styled from "styled-components";
import { ButtonContentProps, ButtonProps } from "./type";

export default function Button({
  size,
  color,
  backgroundColor,
  borderColor,
  radius,
  fontName,
  leftIcon,
  rightIcon,
  children,
  value,
  ...props
}: ButtonProps) {
  const contentProps = { leftIcon, rightIcon, value, children };

  return (
    <StyledButton
      $size={size}
      $color={color}
      $backgroundColor={backgroundColor}
      $borderColor={borderColor}
      $fontName={fontName}
      $radius={radius}
      {...props}>
      <ButtonContent {...contentProps} />
    </StyledButton>
  );
}

const ButtonContent = ({
  leftIcon,
  value,
  rightIcon,
  children,
}: ButtonContentProps) => {
  return (
    <>
      {leftIcon}
      {value}
      {children}
      {rightIcon}
    </>
  );
};

const StyledButton = styled.button<{
  $size?: { width: number; height: number };
  $color?: ColorName;
  $backgroundColor?: ColorName;
  $borderColor?: ColorName;
  $fontName?: FontName;
  $radius?: Radius;
}>`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  cursor: pointer;

  width: ${({ $size }) => $size?.width + "px"};
  height: ${({ $size }) => $size?.height + "px"};
  color: ${({ $color, theme: { color } }) => $color && color[$color]};
  font: ${({ $fontName, theme: { font } }) => $fontName && font[$fontName]};

  border-radius: ${({ $radius, theme: { radius } }) => {
    return $radius && radius[$radius];
  }};

  background: ${({ $backgroundColor, theme: { color } }) => {
    return $backgroundColor && color[$backgroundColor];
  }};

  border: ${({ $borderColor, theme: { color } }) => {
    return $borderColor && `1px solid ${color[$borderColor]}`;
  }};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  svg {
    filter: ${({ $color, theme: { filter } }) => $color && filter[$color]};
  }
`;

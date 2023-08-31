import { HEIGHT } from "@styles/constants";
import { ColorName } from "@styles/designSystem";
import { styled } from "styled-components";

type TopBarProps = {
  title?: string;
  backgroundColor?: ColorName;
  isWithBorder?: boolean;
  isScrolled?: boolean;
  leftBtn?: React.ReactNode;
  rightBtn?: React.ReactNode;
};

export default function TopBar({
  title,
  backgroundColor,
  isWithBorder,
  isScrolled,
  leftBtn,
  rightBtn,
}: TopBarProps) {
  return (
    <StyledTopBar
      $backgroundColor={backgroundColor}
      $isWithBorder={isWithBorder}
      $isScrolled={isScrolled}>
      <Title>
        <span>{title}</span>
      </Title>
      <Buttons>
        {leftBtn}
        {rightBtn}
      </Buttons>
    </StyledTopBar>
  );
}

const StyledTopBar = styled.header<{
  $backgroundColor?: ColorName;
  $isWithBorder?: boolean;
  $isScrolled?: boolean;
}>`
  width: 100%;
  height: ${HEIGHT.topBar}px;
  position: sticky;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${({ $isScrolled, $backgroundColor, theme: { color } }) =>
    $isScrolled !== undefined
      ? $isScrolled && $backgroundColor && color[$backgroundColor]
      : $backgroundColor && color[$backgroundColor]};
  backdrop-filter: ${({ $backgroundColor, theme: { backdropFilter } }) => {
    const isNeutralBackgroundBlur =
      $backgroundColor === "neutralBackgroundBlur";
    return isNeutralBackgroundBlur && backdropFilter.blur;
  }};
  border-bottom: ${({ $isWithBorder, theme: { color } }) =>
    $isWithBorder ? `0.8px solid ${color.neutralBorder}` : "none"};
  transition: background-color 0.5s linear;
`;

const Title = styled.div`
  width: 130px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  font: ${({ theme: { font } }) => font.displayStrong16};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const Buttons = styled.div`
  width: 100%;
  padding: 0 16px;
  box-sizing: border-box;
  position: absolute;
  display: flex;
  justify-content: space-between;
`;

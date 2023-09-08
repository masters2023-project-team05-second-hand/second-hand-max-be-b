import { DefaultTheme } from "styled-components";

export type FontName = keyof typeof font;
export type ColorName = keyof typeof designSystem.color;
export type Radius = keyof typeof radius;

const colors = {
  white: "#FFF",
  grey50: "#FAFAFA",
  grey100: "#F9F9F9CC",
  grey200: "#F5F5F5B2",
  grey300: "#B3B3B31F",
  grey400: "#EFEFF0",
  grey500: "#B3B3B363",
  grey600: "#00000033",
  grey700: "#3C3C435C",
  grey800: "#3C3C4399",
  grey900: "#3C3C43",
  black: "#000",
  purple: "#BA86DA",
  yellow: "#FFCC00",
  red: "#FF3B30",
};

const radius = {
  6: "6px",
  8: "8px",
  12: "12px",
  16: "16px",
  50: "50px",
  half: "50%",
};

const font = {
  displayStrong32: "700 32px Noto Sans KR, sans-serif",
  displayStrong20: "700 20px Noto Sans KR, sans-serif",
  displayStrong16: "700 16px Noto Sans KR, sans-serif",
  displayDefault16: "400 16px Noto Sans KR, sans-serif",
  displayDefault12: "400 12px Noto Sans KR, sans-serif",

  availableStrong16: "700 16px Noto Sans KR, sans-serif",
  availableStrong12: "700 12px Noto Sans KR, sans-serif",
  availableStrong10: "700 10px Noto Sans KR, sans-serif",
  availableDefault16: "400 16px Noto Sans KR, sans-serif",
  availableDefault12: "400 12px Noto Sans KR, sans-serif",

  enabledStrong16: "700 16px Noto Sans KR, sans-serif",
  enabledStrong10: "700 10px Noto Sans KR, sans-serif",
};

const opacity = {
  hover: 0.8,
  press: 0.64,
  disabled: 0.32,
  transparent: 0.04,
};

export const designSystem = {
  color: {
    neutralText: colors.grey900,
    neutralTextWeak: colors.grey800,
    neutralTextStrong: colors.black,
    neutralBackground: colors.white,
    neutralBackgroundWeak: colors.grey50,
    neutralBackgroundBold: colors.grey400,
    neutralBackgroundBlur: colors.grey100,
    neutralBorder: colors.grey500,
    neutralBorderStrong: colors.grey700,
    neutralOverlay: colors.grey600,

    accentText: colors.white,
    accentTextWeak: colors.black,
    accentPrimary: colors.purple,
    accentSecondary: colors.yellow,

    systemWarning: colors.red,
    systemBackground: colors.white,
    systemBackgroundWeak: colors.grey200,
  },
  filter: {
    neutralTextWeak:
      "opacity(60%) brightness(0) saturate(100%) invert(20%) sepia(2%) saturate(3198%) hue-rotate(202deg) brightness(93%) contrast(83%)",
    neutralText:
      "invert(21%) sepia(2%) saturate(2651%) hue-rotate(202deg) brightness(95%) contrast(87%)",
    neutralTextStrong:
      "invert(0%) sepia(100%) saturate(3089%) hue-rotate(210deg) brightness(99%) contrast(109%)",
    neutralBackground:
      "invert(100%) sepia(0%) saturate(0%) hue-rotate(299deg) brightness(105%) contrast(101%)",
    neutralBackgroundWeak:
      "invert(100%) sepia(100%) saturate(0%) hue-rotate(115deg) brightness(107%) contrast(96%)",
    neutralBackgroundBold:
      "invert(100%) sepia(13%) saturate(400%) hue-rotate(180deg) brightness(99%) contrast(89%)",
    neutralBackgroundBlur: "",
    neutralBorder: "",
    neutralBorderStrong: "",
    neutralOverlay: "",

    accentText:
      "invert(100%) sepia(97%) saturate(15%) hue-rotate(110deg) brightness(103%) contrast(102%)",
    accentTextWeak:
      "invert(0%) sepia(90%) saturate(7500%) hue-rotate(167deg) brightness(83%) contrast(109%)",
    accentPrimary:
      "invert(38%) sepia(22%) saturate(3963%) hue-rotate(220deg) brightness(85%) contrast(97%)",
    accentSecondary:
      "invert(87%) sepia(23%) saturate(4965%) hue-rotate(359deg) brightness(101%) contrast(104%)",

    systemWarning:
      "invert(36%) sepia(25%) saturate(6170%) hue-rotate(341deg) brightness(98%) contrast(105%)",
    systemBackground:
      "invert(100%) sepia(99%) saturate(23%) hue-rotate(3deg) brightness(104%) contrast(100%)",
    systemBackgroundWeak: "",
  },
  backdropFilter: {
    blur: "blur(8px)",
  },
  font,
  opacity,
  radius,
};

export const theme: DefaultTheme = {
  ...designSystem,
};

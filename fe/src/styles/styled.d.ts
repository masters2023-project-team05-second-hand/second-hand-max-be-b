import { designSystem } from "./designSystem";

declare module "styled-components" {
  export interface DefaultTheme {
    color: typeof designSystem.color;
    filter: typeof designSystem.filter;
    backdropFilter: typeof designSystem.backdropFilter;
    radius: typeof designSystem.radius;
    font: typeof designSystem.font;
    opacity: typeof designSystem.opacity;
  }
}

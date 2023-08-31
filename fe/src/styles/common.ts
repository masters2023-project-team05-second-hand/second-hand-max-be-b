import styled from "styled-components";
import { HEIGHT, WIDTH } from "./constants";

export const Page = styled.div`
  position: relative;
  margin: 0 auto;
  max-width: ${WIDTH.app}px;
  min-height: 100vh;
  height: 100vh;
  overflow: scroll;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  padding-bottom: ${HEIGHT.navigationBar}px;
  box-sizing: border-box;

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const Main = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: calc(100vh - ${HEIGHT.topBar + HEIGHT.navigationBar}px);
  padding: 0 16px;
  gap: 28px;
`;

export const SubTitle = styled.h2`
  font: ${({ theme: { font } }) => font.availableStrong16};
  color: ${({ theme: { color } }) => color.neutralText};
`;

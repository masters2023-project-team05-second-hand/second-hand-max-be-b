import styled from "styled-components";
import { HEIGHT, WIDTH } from "./constants";

export const Page = styled.div`
  position: relative;
  margin: 0 auto;
  width: ${WIDTH.page}px;
  height: calc(100vh - ${HEIGHT.navigationBar}px);
  overflow: scroll;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  box-sizing: border-box;

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const StaticPage = styled(Page)`
  height: 100vh;
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

export const Dim = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: ${({ theme: { color } }) => color.neutralOverlay};
  z-index: -1;
`;

export const BottomBar = styled.div`
  display: flex;
  align-items: center;
  position: fixed;
  bottom: 0;
  background-color: ${({ theme: { color } }) => color.neutralBackgroundWeak};
  border-top: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  width: ${WIDTH.page}px;
  height: ${HEIGHT.navigationBar}px;
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
`;

export const Target = styled.div`
  height: 1px;
`;

export const PageContent = styled.div`
  max-width: 100%;
`;

export const TextDefault = styled.span`
  display: block;
  max-width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: ${({ theme: { color } }) => color.neutralText};
  font: ${({ theme: { font } }) => font.displayDefault16};
`;

export const TextWeak = styled.span`
  color: ${({ theme: { color } }) => color.neutralTextWeak};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

export const TextBold = styled.span`
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.displayStrong16};
`;

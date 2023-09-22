import styled from "styled-components";
import { HEIGHT, WIDTH } from "./constants";

// 참고: scroll을 Page에서 제어하고 있어서 min-height 하단바의 높이만큼 빼줘야 끝까지 스크롤 가능
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

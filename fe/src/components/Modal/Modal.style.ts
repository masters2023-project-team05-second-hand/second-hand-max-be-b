import styled from "styled-components";

export const ListPanel = styled.ul`
  width: 100%;
  overflow: hidden;
  overflow-y: scroll;

  li:not(:last-child) {
    border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  }

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const ListItem = styled.li<{ $active: boolean }>`
  font: ${({ $active, theme: { font } }) =>
    $active ? font.availableStrong16 : font.availableDefault16};
  color: ${({ $active, theme: { color } }) =>
    $active ? color.neutralTextStrong : color.neutralText};
  padding: 16px 0px;

  &:hover {
    cursor: pointer;
  }
`;

import styled from "styled-components";

export const SubInfo = styled.span`
  display: flex;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralTextWeak};
`;

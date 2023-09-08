import ErrorIcon from "@assets/image/error.gif";
import LoadingIndicator from "@assets/image/loading.gif";
import { SubTitle } from "@styles/common";
import styled from "styled-components";

export function Loading({ messages }: { messages: string[] }) {
  return (
    <Container>
      <img src={LoadingIndicator} alt="loading-indicator" />
      {messages.map((message) => (
        <SubTitle key={message}>{message}</SubTitle>
      ))}
    </Container>
  );
}

export function Error({ messages }: { messages: string[] }) {
  return (
    <Container>
      <img src={ErrorIcon} alt="error-icon" />
      {messages.map((message) => (
        <SubTitle key={message}>{message}</SubTitle>
      ))}
    </Container>
  );
}

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 100px;

  img {
    margin: 30px;
  }
`;

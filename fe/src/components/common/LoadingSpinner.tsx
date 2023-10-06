import { keyframes, styled } from "styled-components";

export function LoadingSpinner({ text }: { text?: string }) {
  return (
    <Wrapper>
      <Indicator />
      {text && <div>{text}</div>}
    </Wrapper>
  );
}

const spinner = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: absolute;
  gap: 16px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const Indicator = styled.div`
  width: 15px;
  height: 15px;
  border: 10px solid #f3f3f3;
  border-top: 10px solid #383636;
  border-radius: 50%;
  animation: ${spinner} 1.5s linear infinite;
`;

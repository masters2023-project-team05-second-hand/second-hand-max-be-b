import { ReactComponent as ErrorIcon } from "@assets/icon/error.svg";
import { ReactComponent as InfoIcon } from "@assets/icon/info.svg";
import { ReactComponent as SuccessIcon } from "@assets/icon/success.svg";
import { HEIGHT } from "@styles/constants";
import { useAtomValue, useSetAtom } from "jotai";
import { useEffect, useState } from "react";
import { Toaster as ToasterType, useToasterAtom } from "store/toaster";
import styled from "styled-components";

export default function Toaster() {
  const toasts = useAtomValue(useToasterAtom);

  return (
    <StyledToaster>
      {toasts.map((toast: ToasterType) => (
        <Toast key={toast.id} {...toast} />
      ))}
    </StyledToaster>
  );
}

function Toast({ id, type, title, message }: ToasterType) {
  const setToaster = useSetAtom(useToasterAtom);
  const [opacity, setOpacity] = useState(0.2);

  useEffect(() => {
    setOpacity(1);

    const removeTimeout = setTimeout(() => {
      setToaster({ type: "remove", payload: { id } });
    }, TOAST_DURATION);

    return () => {
      clearTimeout(removeTimeout);
    };
  }, [id, setToaster]);

  useEffect(() => {
    const visibilityTimeout = setTimeout(() => {
      setOpacity(0);
    }, TOAST_DURATION - ANIMATION_DURATION);

    return () => {
      clearTimeout(visibilityTimeout);
    };
  }, []);

  return (
    <StyledToast
      $opacity={opacity}
      $animationDirection={opacity === 1 ? "enter" : "exit"}>
      <div className="toast-container">
        <ToastIcon type={type} />
        <ToastText>
          <span className="toast-title">{title}</span>
          <span className="toast-message">{message}</span>
        </ToastText>
      </div>
    </StyledToast>
  );
}

const TOAST_DURATION = 3000;
const ANIMATION_DURATION = 1000;

const StyledToaster = styled.div`
  position: fixed;
  display: flex;
  flex-direction: column;
  gap: 8px;
  bottom: ${HEIGHT.navigationBar}px;
  left: calc(50% - 160px);
  z-index: 100;
`;

const StyledToast = styled.div<{
  $opacity: number;
  $animationDirection: "enter" | "exit";
}>`
  opacity: ${({ $opacity }) => $opacity};
  transition: all ${ANIMATION_DURATION}ms ease-in-out;
  transform: translateY(
    ${({ $animationDirection }) =>
      $animationDirection === "enter" ? "-50%" : "50%"}
  );

  .toast-container {
    width: 330px;
    display: flex;
    align-items: flex-start;
    justify-content: center;
    gap: 8px;
    padding: 16px 0;
    background-color: ${({ theme: { color } }) => color.neutralOverlay};
    border-radius: ${({ theme: { radius } }) => radius[8]};
  }
`;

const ToastText = styled.div`
  width: 80%;
  display: flex;
  flex-direction: column;
  gap: 4px;

  .toast-title {
    font: ${({ theme: { font } }) => font.displayStrong16};
    color: ${({ theme: { color } }) => color.neutralText};
  }

  .toast-message {
    font: ${({ theme: { font } }) => font.availableDefault12};
  }
`;

const ToastIcon = ({ type }: { type: "success" | "error" | "info" }) => {
  switch (type) {
    case "success":
      return <SuccessIcon />;
    case "error":
      return <ErrorIcon />;
    case "info":
      return <InfoIcon />;
    default:
      return null;
  }
};

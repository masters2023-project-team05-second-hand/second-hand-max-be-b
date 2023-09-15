import { Dim } from "@styles/common";
import React, { LegacyRef } from "react";
import { createPortal } from "react-dom";
import styled from "styled-components";
import ModalHeader, { ModalHeaderProps } from "./ModalHeader";

type ModalProps = {
  ref: LegacyRef<HTMLDivElement>;
  header: ModalHeaderProps;
  content: React.ReactNode;
};

const Modal = React.forwardRef<HTMLDivElement, ModalProps>(
  ({ header, content }, ref) => {
    return createPortal(
      <Dim>
        <StyledModal ref={ref}>
          <ModalHeader {...header} />
          <ModalContent>{content}</ModalContent>
        </StyledModal>
      </Dim>,
      document.getElementById("modal-root")!
    );
  }
);

const StyledModal = styled.div`
  position: fixed;
  top: calc(50% - 300px);
  left: calc(50% - 160px);
  width: 320px;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  border-radius: ${({ theme: { radius } }) => radius[16]};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
`;

const ModalContent = styled.main`
  max-height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 1.5rem;
`;

export default Modal;

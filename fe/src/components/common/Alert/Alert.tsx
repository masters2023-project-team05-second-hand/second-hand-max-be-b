import Button from "@components/common/Buttons/Button";
import useOutsideClick from "@hooks/useOutsideClick";
import { styled } from "styled-components";

type AlertType = {
  message: string;
  closeAlertHandler: () => void;
  onDeleteClick: () => void;
};

export default function Alert({
  message,
  closeAlertHandler,
  onDeleteClick,
}: AlertType) {
  const { ref: modalRef } =
    useOutsideClick<HTMLDialogElement>(closeAlertHandler);

  const deleteHandler = () => {
    onDeleteClick();
    closeAlertHandler();
  };

  return (
    <StyledAlert ref={modalRef}>
      <span>{message}</span>
      <ButtonWrapper>
        <Button
          color="accentTextWeak"
          fontName="displayDefault16"
          value="취소"
          onClick={closeAlertHandler}
        />
        <Button
          color="systemWarning"
          fontName="displayStrong16"
          value="삭제"
          onClick={deleteHandler}
        />
      </ButtonWrapper>
    </StyledAlert>
  );
}

const StyledAlert = styled.dialog`
  position: fixed;
  top: 50%;
  transform: translateY(-100%);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 24px 32px;
  width: 336px;
  height: 144px;
  box-sizing: border-box;
  border: none;
  box-shadow: 0px 4px 4px 0px #00000040;
  z-index: 1;
  border-radius: ${({ theme: { radius } }) => radius[16]};
  font: ${({ theme: { font } }) => font.displayStrong16};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 32px;
  margin-left: auto;
`;

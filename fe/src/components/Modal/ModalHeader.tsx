import { ReactComponent as LeftIcon } from "@assets/icon/chevron-left.svg";
import { ReactComponent as XIcon } from "@assets/icon/x.svg";
import Button from "@components/common/Buttons/Button";
import styled from "styled-components";

export type ModalHeaderProps = {
  title?: string;
  backHandler?: () => void;
  closeHandler: () => void;
};

export default function ModalHeader({
  title,
  backHandler,
  closeHandler,
}: ModalHeaderProps) {
  return (
    <StyledModalHeader>
      {backHandler && <Button leftIcon={<LeftIcon />} onClick={backHandler} />}
      {title && <HeadlineText>{title}</HeadlineText>}
      <Button leftIcon={<XIcon />} onClick={closeHandler} />
    </StyledModalHeader>
  );
}

const StyledModalHeader = styled.header`
  height: 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 24px;
`;

const HeadlineText = styled.h1`
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

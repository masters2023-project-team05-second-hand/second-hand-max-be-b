import { ReactComponent as MapIcon } from "@assets/icon/map-pin-filled.svg";
import { HEIGHT, WIDTH } from "@styles/constants";
import { styled } from "styled-components";
type AddressButtonProps = {
  addressName: string;
  onOpenAddressModal: () => void;
};

export default function AddressButton({
  addressName,
  onOpenAddressModal,
}: AddressButtonProps) {
  return (
    <StyledAddressButton onClick={onOpenAddressModal}>
      <MapIcon />
      <span>{addressName}</span>
    </StyledAddressButton>
  );
}

const StyledAddressButton = styled.button`
  display: flex;
  align-items: center;
  gap: 8px;
  position: fixed;
  bottom: 0;
  padding: 0 16px;
  box-sizing: border-box;
  width: ${WIDTH.app}px;
  height: ${HEIGHT.navigationBar}px;
  border-top: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundWeak};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};
`;

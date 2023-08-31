import { ReactComponent as CircleXIcon } from "@assets/icon/circle-x-filled.svg";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import Button from "@components/common/Buttons/Button";
import { AddressInfo } from "api/type";
import styled from "styled-components";

export default function AddressIndicatorList({
  userAddressList,
  currentUserAddressId,
  onClickAddressAddButton,
}: {
  userAddressList: AddressInfo[];
  currentUserAddressId: number;
  onClickAddressAddButton: () => void;
}) {
  return (
    <StyledAddressIndicatorList>
      <NoticeText>
        <span>지역은 최소 1개,</span>
        <span>최대 2개까지 설정 가능해요.</span>
      </NoticeText>
      <div className="button-wrapper">
        {userAddressList.map(({ id, name }) => (
          <AddressIndicator
            key={id}
            $active={id === currentUserAddressId}
            onClick={() => console.log(`${id} 선택한 동네 변경`)}>
            <span>{name}</span>
            <Button
              leftIcon={<CircleXIcon />}
              color="accentText"
              onClick={(e) => {
                e.stopPropagation();
                console.log(
                  `${name}: userAddressList 개수에 따라 동적으로 결정`
                );
              }}
            />
          </AddressIndicator>
        ))}
        <Button
          size={{ width: 288, height: 56 }}
          leftIcon={<PlusIcon />}
          fontName="availableStrong16"
          color="accentTextWeak"
          borderColor="neutralBorder"
          radius={8}
          value="추가"
          onClick={onClickAddressAddButton}
        />
      </div>
    </StyledAddressIndicatorList>
  );
}

const StyledAddressIndicatorList = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  .button-wrapper {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }
`;

const AddressIndicator = styled.div<{ $active: boolean }>`
  display: flex;
  justify-content: space-between;
  padding: 0 16px;
  align-items: center;
  height: 56px;
  cursor: pointer;

  background-color: ${({ $active, theme: { color } }) =>
    $active ? color.accentPrimary : color.neutralOverlay};
  border-radius: ${({ theme: { radius } }) => radius[8]};
  font: ${({ theme: { font } }) => font.availableStrong16};
  color: ${({ theme: { color } }) => color.accentText};
`;

const NoticeText = styled.div`
  display: flex;
  flex-direction: column;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralText};
  padding-top: 48px;

  span {
    display: flex;
    justify-content: center;
  }
`;

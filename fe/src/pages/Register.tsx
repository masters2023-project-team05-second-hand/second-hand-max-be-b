import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import AddressModal from "@components/Modal/AddressModal/AddressModal";
import TopBar from "@components/TopBar";
import UserAccount from "@components/UserAccount";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { Main, Page } from "@styles/common";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAddressList } from "store";

export default function Register() {
  const [addresses] = useAddressList();

  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);
  const navigate = useNavigate();

  const openAddressModal = () => setIsAddressModalOpen(true);
  const closeAddressModal = () => setIsAddressModalOpen(false);

  return (
    <Page>
      <TopBar
        title="회원가입"
        backgroundColor="neutralBackgroundBlur"
        isWithBorder={true}
        rightBtn={
          <Button
            value="완료"
            color="neutralText"
            fontName="availableStrong16"
            onClick={() => navigate(ROUTE_PATH.home)}
            disabled={addresses.length === 0}
          />
        }
      />
      <Main>
        <UserAccount />
        <Button
          size={{ width: 330, height: 56 }}
          value="위치 추가"
          borderColor="neutralBorder"
          radius={8}
          fontName="availableStrong16"
          leftIcon={<PlusIcon />}
          onClick={openAddressModal}
        />
        {isAddressModalOpen && (
          <AddressModal closeHandler={closeAddressModal} />
        )}
      </Main>
    </Page>
  );
}

import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import AddressModal from "@components/Modal/AddressModal/AddressModal";
import TopBar from "@components/TopBar";
import UserAccount from "@components/UserAccount";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { Main, StaticPage } from "@styles/common";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAddressListValue } from "store";

// 주소가 있는 유저는 접근할 수 없는 페이지
export default function Register() {
  const addresses = useAddressListValue();

  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);
  const navigate = useNavigate();

  const openAddressModal = () => setIsAddressModalOpen(true);
  const closeAddressModal = () => setIsAddressModalOpen(false);

  useEffect(() => {
    if (addresses.length > 0) {
      navigate(ROUTE_PATH.home);
    }
  }, [addresses, navigate]);

  return (
    <StaticPage>
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
    </StaticPage>
  );
}

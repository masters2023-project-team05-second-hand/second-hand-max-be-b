import { AddressInfo } from "api/type";
import { currentUserAddressId, userAddressList } from "mocks/data/address";
import { useState } from "react";
import Modal from "../Modal";
import AddressIndicatorList from "./Content/AddressIndicatorList";
import AddressSearch from "./Content/AddressSearch";

export default function AddressModal({
  closeHandler,
}: {
  closeHandler: () => void;
}) {
  const [newAddressList] = useState<AddressInfo[]>(userAddressList);
  const [isSearchingAddress, setIsSearchingAddress] = useState<boolean>(false);

  const openAddressSearch = () => setIsSearchingAddress(true);
  const closeAddressSearch = () => setIsSearchingAddress(false);

  const addressSearchHeaderProps = {
    backHandler: closeAddressSearch,
    closeHandler: () => console.log("Close AddressModal"),
  };

  const addressIndicatorListHeaderProps = {
    title: "동네 설정",
    closeHandler: () => {
      console.log("동네 수정 요청 보내기 / 변경 사항 없으면 모달 닫기");
      closeHandler();
    },
  };

  const userAddressIDs = newAddressList.map(({ id }) => id);

  const currentHeaderProps = isSearchingAddress
    ? addressSearchHeaderProps
    : addressIndicatorListHeaderProps;

  const currentContent = isSearchingAddress ? (
    <AddressSearch {...{ closeAddressSearch, userAddressIDs }} />
  ) : (
    <AddressIndicatorList
      userAddressList={newAddressList}
      onClickAddressAddButton={openAddressSearch}
      currentUserAddressId={currentUserAddressId}
    />
  );

  return (
    <Modal
      headerProps={currentHeaderProps}
      content={currentContent}
      closeHandler={closeHandler}
    />
  );
}

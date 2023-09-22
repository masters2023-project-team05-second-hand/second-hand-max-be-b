import { AddressInfo } from "@api/type";
import { putUserAddress } from "@api/user";
import { useToast } from "@hooks/useToast";
import { useMutation } from "@tanstack/react-query";
import { isSameItems } from "@utils/index";
import { useState } from "react";
import { useAddressList, useCurrentAddressId } from "store";
import Modal from "../Modal";
import AddressIndicatorList from "./Content/AddressIndicatorList";
import AddressSearch from "./Content/AddressSearch";

export default function AddressModal({
  closeHandler,
}: {
  closeHandler: () => void;
}) {
  const { toast } = useToast();

  const [addresses, setAddresses] = useAddressList();
  const [currentAddressId, setCurrentAddressId] = useCurrentAddressId();

  const [currentAddresses, setCurrentAddresses] = useState([...addresses]);
  const [currentSelectedAddressId, setCurrentSelectedAddressId] = useState(
    currentAddressId ?? -1
  );

  const changeCurrentAddresses = (newAddresses: AddressInfo[]) => {
    setCurrentAddresses(newAddresses);
  };
  const changeCurrentAddressId = (newAddressId: number) => {
    setCurrentSelectedAddressId(newAddressId);
  };

  const [isSearchingAddress, setIsSearchingAddress] = useState<boolean>(false);
  const openAddressSearch = () => setIsSearchingAddress(true);
  const closeAddressSearch = () => setIsSearchingAddress(false);

  const { mutate: mutateUserAddresses } = useMutation(putUserAddress, {
    onSuccess: () => {
      toast({
        type: "success",
        title: "동네 설정 완료",
        message: "동네 설정이 완료되었습니다.",
      });
      setAddresses(currentAddresses);
    },
    onError: () => {
      toast({
        type: "error",
        title: "동네 설정 실패",
        message: "동네 설정에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
    },
  });

  const addressSearchHeader = {
    backHandler: closeAddressSearch,
    closeHandler,
  };

  const userAddressIDs = addresses.map(({ id }) => id);
  const currentAddressIDs = currentAddresses.map(({ id }) => id);

  const addressIndicatorListHeader = {
    title: "동네 설정",
    closeHandler: () => {
      const isSameAddresses = isSameItems(userAddressIDs, currentAddressIDs);
      const isSameAddressId = currentAddressId === currentSelectedAddressId;

      !isSameAddresses && mutateUserAddresses(currentAddressIDs);
      !isSameAddressId && setCurrentAddressId(currentSelectedAddressId);
      closeHandler();
    },
  };

  const currentHeader = isSearchingAddress
    ? addressSearchHeader
    : addressIndicatorListHeader;

  const currentContent = isSearchingAddress ? (
    <AddressSearch
      {...{
        closeAddressSearch,
        currentAddressIDs,
        currentAddresses,
        changeCurrentAddresses,
        changeCurrentAddressId,
      }}
    />
  ) : (
    <AddressIndicatorList
      {...{
        currentAddresses,
        currentSelectedAddressId,
        changeCurrentAddresses,
        changeCurrentAddressId,
        openAddressSearch,
      }}
    />
  );

  return <Modal header={currentHeader} content={currentContent} />;
}

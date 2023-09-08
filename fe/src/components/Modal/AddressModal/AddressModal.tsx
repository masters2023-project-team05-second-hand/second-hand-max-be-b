import { useToast } from "@hooks/useToast";
import { useMutation } from "@tanstack/react-query";
import { isSameItems } from "@utils/index";
import { putUserAddress } from "api";
import { useState } from "react";
import { useAddressList } from "store";
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
  const userAddressIDs = addresses.map(({ id }) => id);

  const [prevAddresses] = useState(addresses);
  const prevAddressIDs = prevAddresses.map(({ id }) => id);

  const [isSearchingAddress, setIsSearchingAddress] = useState<boolean>(false);
  const openAddressSearch = () => setIsSearchingAddress(true);
  const closeAddressSearch = () => setIsSearchingAddress(false);

  const userAddressMutation = useMutation(putUserAddress, {
    onSuccess: () =>
      toast({
        type: "success",
        title: "동네 설정 완료",
        message: "동네 설정이 완료되었습니다.",
      }),
    onError: () => {
      toast({
        type: "error",
        title: "동네 설정 실패",
        message: "동네 설정에 실패했습니다. 잠시 후 다시 시도해주세요.",
      }),
        setAddresses(prevAddresses);
    },
  });

  const addressSearchHeaderProps = {
    backHandler: closeAddressSearch,
    closeHandler,
  };

  const addressIndicatorListHeaderProps = {
    title: "동네 설정",
    closeHandler: () => {
      !isSameItems(userAddressIDs, prevAddressIDs) &&
        userAddressMutation.mutate(userAddressIDs);
      closeHandler();
    },
  };

  const currentHeaderProps = isSearchingAddress
    ? addressSearchHeaderProps
    : addressIndicatorListHeaderProps;

  const currentContent = isSearchingAddress ? (
    <AddressSearch {...{ closeAddressSearch, userAddressIDs }} />
  ) : (
    <AddressIndicatorList openAddressSearch={openAddressSearch} />
  );

  return <Modal headerProps={currentHeaderProps} content={currentContent} />;
}

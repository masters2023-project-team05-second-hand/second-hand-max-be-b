import { ListItem, ListPanel } from "@components/Modal/Modal.style";
import { addresses1 } from "mocks/data/address";
import { useState } from "react";
import styled from "styled-components";

export default function AddressSearch({
  userAddressIDs,
  closeAddressSearch,
}: {
  userAddressIDs: number[];
  closeAddressSearch: () => void;
}) {
  // TODO: 동네 검색/전체 동네 조회 요청으로 받아온 데이터를 allAddressList로 상태 관리해야 함
  const [allAddressList] = useState(addresses1.addresses);
  // const [searchKeyword, setSearchKeyword] = useState<string>("");

  return (
    <>
      <SearchBar placeholder="동명(읍, 면)으로 검색(ex. 서초동)" />
      <ListPanel>
        {allAddressList.map(({ id, name }) => (
          <ListItem
            key={id}
            $active={userAddressIDs.includes(id)}
            onClick={() => {
              console.log(
                `${id} 선택한 동네 변경, context로 선택한 item 정보 AddressIndicator와 공유`
              );
              closeAddressSearch();
            }}>
            <span>{name}</span>
          </ListItem>
        ))}
      </ListPanel>
    </>
  );
}

const SearchBar = styled.input`
  width: 100%;
  height: 40px;
  padding: 8px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};

  &::placeholder {
    font: ${({ theme: { font } }) => font.availableDefault16};
    color: ${({ theme: { color } }) => color.neutralTextWeak};
  }
`;

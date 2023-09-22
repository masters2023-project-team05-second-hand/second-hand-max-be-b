import { useAddressesInfiniteQuery } from "@api/product/queries";
import { ListItem, ListPanel } from "@components/Modal/Modal.style";
import { Error, Loading } from "@components/common/Guide";
import { useIntersect } from "@hooks/useIntersect";
import { Target } from "@styles/common";
import { AddressInfo } from "api/type";
import { debounce } from "lodash";
import { useEffect, useState } from "react";
import styled from "styled-components";

export default function AddressSearch({
  currentAddressIDs,
  currentAddresses,
  closeAddressSearch,
  changeCurrentAddresses,
  changeCurrentAddressId,
}: {
  currentAddressIDs: number[];
  currentAddresses: AddressInfo[];
  closeAddressSearch: () => void;
  changeCurrentAddresses: (newAddresses: AddressInfo[]) => void;
  changeCurrentAddressId: (newAddressId: number) => void;
}) {
  const [userInput, setUserInput] = useState("");

  // TODO: 검색어 쿼리를 위한 상태를 따로 만들어야 할까? 다른 방법은?
  const [searchWord, setSearchWord] = useState("");
  const {
    data: addressList,
    status,
    isFetching,
    hasNextPage,
    fetchNextPage,
  } = useAddressesInfiniteQuery({ searchWord });

  useEffect(() => {
    const DEBOUNCE_TIME = 500;

    const debounceChangeSearchWord = debounce(() => {
      setSearchWord(userInput);
    }, DEBOUNCE_TIME);

    debounceChangeSearchWord();

    return () => {
      debounceChangeSearchWord.cancel();
    };
  }, [userInput]);

  const onAddAddress = (item: AddressInfo) => {
    const isMaxAddressCount = currentAddressIDs.length === 2;
    const isAlreadyAdded = currentAddressIDs.some((id) => id === item.id);

    if (isAlreadyAdded || isMaxAddressCount) return;

    changeCurrentAddresses([...currentAddresses, item]);
    changeCurrentAddressId(item.id);
  };

  const targetRef = useIntersect(() => {
    if (hasNextPage) {
      fetchNextPage();
    }
  });

  const onChangeSearchWord = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserInput(e.target.value);
  };

  return (
    <>
      <SearchBar
        placeholder="동명(읍, 면)으로 검색(ex. 서초동)"
        value={userInput}
        onChange={onChangeSearchWord}
      />
      {status === "loading" && <Loading messages={["로딩중..."]} />}
      {status === "error" && (
        <Error messages={["잠시 후 다시 시도해주세요."]} />
      )}
      {status === "success" && (
        <ListPanel>
          {addressList.pages.map(({ addresses }) =>
            addresses.map(({ id, name }) => (
              <ListItem
                key={id}
                $active={currentAddressIDs.includes(id)}
                onClick={() => {
                  onAddAddress({ id, name });
                  closeAddressSearch();
                }}>
                <span>{name}</span>
              </ListItem>
            ))
          )}
          {isFetching ? (
            <Loading messages={["주소 목록 로딩 중"]} />
          ) : (
            <Target ref={targetRef} />
          )}
        </ListPanel>
      )}
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

import { useAddressesInfiniteQuery } from "@api/product/queries";
import { ListItem, ListPanel } from "@components/Modal/Modal.style";
import { Error, Loading } from "@components/common/Guide";
import { useIntersect } from "@hooks/useIntersect";
import { Target } from "@styles/common";
import { AddressInfo } from "api/type";
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
  const onAddAddress = (item: AddressInfo) => {
    const isMaxAddressCount = currentAddressIDs.length === 2;
    const isAlreadyAdded = currentAddressIDs.some((id) => id === item.id);

    if (isAlreadyAdded || isMaxAddressCount) return;

    changeCurrentAddresses([...currentAddresses, item]);
    changeCurrentAddressId(item.id);
  };

  const { data, status, isFetching, hasNextPage, fetchNextPage } =
    useAddressesInfiniteQuery();

  const ref = useIntersect((entry, observer) => {
    observer.unobserve(entry.target);
    if (hasNextPage && !isFetching) {
      fetchNextPage();
    }
  });

  // TODO: 동네 검색

  return (
    <>
      <SearchBar placeholder="동명(읍, 면)으로 검색(ex. 서초동)" />
      {status === "loading" && <Loading messages={["로딩중..."]} />}
      {status === "error" && (
        <Error messages={["잠시 후 다시 시도해주세요."]} />
      )}
      {status === "success" && (
        <ListPanel>
          {data.pages.map(({ addresses }) =>
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
          <Target ref={ref} />
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

import { useAddressesInfiniteQuery } from "@api/queries";
import { ListItem, ListPanel } from "@components/Modal/Modal.style";
import { Error, Loading } from "@components/common/Guide";
import { useIntersect } from "@hooks/useIntersect";
import { AddressInfo } from "api/type";
import { useAddressList, useCurrentAddressId } from "store";
import styled from "styled-components";

export default function AddressSearch({
  userAddressIDs,
  closeAddressSearch,
}: {
  userAddressIDs: number[];
  closeAddressSearch: () => void;
}) {
  const [addresses, setAddresses] = useAddressList();
  const [, setCurrentAddressId] = useCurrentAddressId();

  const onAddAddress = (item: AddressInfo) => {
    const isMaxAddressCount = addresses.length === 2;
    const isAlreadyAdded = addresses.some(({ id }) => id === item.id);

    if (isAlreadyAdded || isMaxAddressCount) return;

    setAddresses([...addresses, item]);
    setCurrentAddressId(item.id);
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
                $active={userAddressIDs.includes(id)}
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

const Target = styled.div`
  height: 1px;
`;

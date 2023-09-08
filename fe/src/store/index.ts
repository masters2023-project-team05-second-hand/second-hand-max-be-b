import { AddressInfo, Member } from "api/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

const lastAddressId = localStorage.getItem("currentAddressId");

const memberAtom = atom<Member>({ nickname: "", profileImgUrl: "" });
const addressListAtom = atom<AddressInfo[]>([]);
const currentAddressIdAtom = atom<number | null>(Number(lastAddressId));
const currentCategoryIdAtom = atom<number | undefined>(undefined);

const useMemberAtom = atom(
  (get) => get(memberAtom),
  (_, set, payload: Member) => {
    set(memberAtom, payload);
  }
);

const useAddressListAtom = atom(
  (get) => get(addressListAtom),
  (_, set, payload: AddressInfo[]) => {
    set(addressListAtom, payload);
  }
);

const useCurrentAddressIdAtom = atom(
  (get) => get(currentAddressIdAtom),
  (_, set, payload: number) => {
    set(currentAddressIdAtom, payload);
    localStorage.setItem("currentAddressId", String(payload));
  }
);

const useCurrentCategoryIdAtom = atom(
  (get) => get(currentCategoryIdAtom),
  (_, set, payload: number) => {
    set(currentCategoryIdAtom, payload);
  }
);

// read & set 모두 필요할 땐 useAtom 사용
// read만 필요할 땐 useAtomValue 사용
// set만 필요할 땐 useSetAtom 사용

export const useMember = () => useAtom(useMemberAtom);
export const useAddressList = () => useAtom(useAddressListAtom);
export const useCurrentAddressId = () => useAtom(useCurrentAddressIdAtom);
export const useCurrentCategoryId = () => useAtom(useCurrentCategoryIdAtom);

export const useMemberValue = () => useAtomValue(memberAtom);
export const useAddressListValue = () => useAtomValue(addressListAtom);
export const useCurrentAddressIdValue = () =>
  useAtomValue(currentAddressIdAtom);
export const useCurrentCategoryIdValue = () =>
  useAtomValue(currentCategoryIdAtom);

export const useSetMember = () => useSetAtom(useMemberAtom);
export const useSetAddresses = () => useSetAtom(useAddressListAtom);
export const useSetCurrentAddressId = () => useSetAtom(useCurrentAddressIdAtom);
export const useSetCurrentCategoryId = () =>
  useSetAtom(useCurrentCategoryIdAtom);

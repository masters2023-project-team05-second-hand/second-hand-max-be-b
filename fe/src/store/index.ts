import { AddressInfo, Member } from "api/type";
import { atom, useAtomValue } from "jotai";

export const memberAtom = atom<Member | null>(null);
export const tokensAtom = atom<{
  accessToken: string;
  refreshToken: string;
}>({
  accessToken: "",
  refreshToken: "",
});

export const currentAddressIdAtom = atom<number | null>(null);
export const addressListAtom = atom<AddressInfo[]>([]);

export const useMember = () => useAtomValue(memberAtom);
export const useTokens = () => useAtomValue(tokensAtom);

export const useAddressList = () => useAtomValue(addressListAtom);
export const useCurrentAddressId = () => useAtomValue(currentAddressIdAtom);

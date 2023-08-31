import { AddressInfo } from "api/type";

// TODO: API 결정되면 수정 필요
export const currentUserAddressId = 1;
export const userAddressList: AddressInfo[] = [
  { id: 1, name: "역삼1동" },
  { id: 5, name: "역삼5동" },
];

export const addresses1 = {
  addresses: Array.from({ length: 20 }, (_, i) => ({
    id: i + 1,
    name: `서울 강남구 역삼${i + 1}동`,
  })),
  hasNext: true,
};

export const addresses2 = {
  addresses: Array.from({ length: 20 }, (_, i) => ({
    id: i + 21,
    name: `서울 강남구 역삼${i + 21}동`,
  })),
  hasNext: true,
};

export const addresses3 = {
  addresses: Array.from({ length: 20 }, (_, i) => ({
    id: i + 31,
    name: `서울 강남구 역삼${i + 31}동`,
  })),
  hasNext: false,
};

import { fetcher } from "@api/fetcher";
import { AddressInfo, Member, ProductList, Tokens } from "@api/type";
import { ENTIRE_STATUS_ID, USER_API_PATH } from "./constants";

export const postSocialLogin = async (
  provider: "kakao" | "github",
  accessCode: string
) => {
  const { data } = await fetcher.post<Tokens>(USER_API_PATH.login(provider), {
    accessCode,
  });
  return data;
};

export const postLogout = async () => {
  const refreshToken = localStorage.getItem("refreshToken");

  return await fetcher.post(USER_API_PATH.logout, { refreshToken });
};

export const postRefreshToken = async (refreshToken: string) => {
  return await fetcher.post<{ accessToken: string }>(USER_API_PATH.refresh, {
    refreshToken,
  });
};

export const putUserAddress = async (addressIds: number[]) => {
  return await fetcher.put<AddressInfo[]>(USER_API_PATH.memberAddress, {
    addressIds,
  });
};

export const patchUserProfile = async (file: File) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };
  const formData = new FormData();
  formData.append("newProfileImg", file);

  return await fetcher.patch<{ updatedImgUrl: string }>(
    USER_API_PATH.userProfile,
    formData,
    config
  );
};

export const getMember = async () => {
  const { data } = await fetcher.get<Member>(USER_API_PATH.member);
  return data;
};

export const getMemberAddress = async () => {
  const { data } = await fetcher.get<AddressInfo[]>(
    USER_API_PATH.memberAddress
  );
  return data;
};

export const patchNickname = async (newNickname: string) => {
  return await fetcher.patch(USER_API_PATH.nickname, { newNickname });
};

export const getProductLikeStatus = async (productId: string) => {
  const path = `${USER_API_PATH.wishlist}/${productId}`;

  const { data } = await fetcher.get<{ isWished: boolean }>(path);
  return data;
};

export const postProductLike = async ({
  productId,
  isWished,
}: {
  productId: string;
  isWished: boolean;
}) => {
  return await fetcher.post(USER_API_PATH.wishlist, {
    productId,
    isWished,
  });
};

export const getUserWishlistCategory = async () => {
  const { data } = await fetcher.get<{ id: number; name: string }[]>(
    `${USER_API_PATH.wishlist}/categories`
  );
  return data;
};

export const getUserWishlistProduct = async ({
  categoryId,
  page = 0,
  size = 10,
}: {
  categoryId: number;
  page: number;
  size: number;
}) => {
  const { data } = await fetcher.get<ProductList>(
    `${USER_API_PATH.wishlist}?categoryId=${categoryId}&page=${page}&size=${size}`
  );
  return data;
};

export const getUserSalesProduct = async ({
  statusId,
  page = 0,
  size = 10,
}: {
  statusId: number;
  page: number;
  size: number;
}) => {
  const { data } = await fetcher.get<ProductList>(
    `${USER_API_PATH.sales}?statusId=${
      statusId ? statusId : ENTIRE_STATUS_ID
    }&page=${page}&size=${size}`
  );
  return data;
};

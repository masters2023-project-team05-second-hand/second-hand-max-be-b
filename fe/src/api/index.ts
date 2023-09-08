import { API_PATH } from "./constants";
import { fetcher } from "./fetcher";
import {
  AddressList,
  CategoryInfo,
  Member,
  ProductDetailInfo,
  Tokens,
  UserAddressInfo,
} from "./type";

export const postSocialLogin = async (
  provider: "kakao" | "github",
  accessCode: string
) => {
  const { data } = await fetcher.post<Tokens>(API_PATH.login(provider), {
    accessCode,
  });
  return data;
};

export const postLogout = async (body: { refreshToken: string }) => {
  return await fetcher.post(API_PATH.logout, body);
};

export const getCategories = async () => {
  const { data } = await fetcher.get<CategoryInfo[]>(API_PATH.categories);
  return data;
};

export const postRefreshToken = async (refreshToken: string) => {
  return await fetcher.post<{ accessToken: string }>(API_PATH.refresh, {
    refreshToken,
  });
};

export const putUserAddress = async (addressIds: number[]) => {
  return await fetcher.put<UserAddressInfo[]>(API_PATH.memberAddress, {
    addressIds,
  });
};

export const postUserProfile = async (file: File) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };
  const formData = new FormData();
  formData.append("image", file);

  return await fetcher.post<{ profileImgUrl: string }>(
    API_PATH.userProfile,
    formData,
    config
  );
};

export const getMember = async () => {
  const { data } = await fetcher.get<Member>(API_PATH.member);
  return data;
};

export const getMemberAddress = async () => {
  const { data } = await fetcher.get<UserAddressInfo[]>(API_PATH.memberAddress);
  return data;
};

export const getAddresses = async (page: number = 0, size: number = 10) => {
  const { data } = await fetcher.get<AddressList>(
    API_PATH.addresses(page, size)
  );
  return data;
};

export const patchNickname = async (nickname: string) => {
  return await fetcher.patch(API_PATH.nickname, { nickname });
};

export const getProductDetail = async (productId: number) => {
  return await fetcher.get<ProductDetailInfo>(
    API_PATH.productDetail(productId)
  );
};

export const postProduct = async (productInfo: FormData) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  return await fetcher.post<{ productId: number }>(
    API_PATH.newProduct,
    productInfo,
    config
  );
};

export const patchProduct = async ({
  productId,
  productInfo,
}: {
  productId: number;
  productInfo: FormData;
}) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  return await fetcher.patch(
    API_PATH.editProduct(productId),
    productInfo,
    config
  );
};

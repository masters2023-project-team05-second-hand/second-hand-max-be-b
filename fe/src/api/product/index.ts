import { fetcher } from "@api/fetcher";
import {
  AddressList,
  CategoryInfo,
  ProductDetailInfo,
  ProductList,
  Status,
} from "@api/type";
import { PRODUCT_API_PATH } from "./constants";

export const getAddresses = async (page: number = 0, size: number = 10) => {
  const { data } = await fetcher.get<AddressList>(
    PRODUCT_API_PATH.addresses(page, size)
  );
  return data;
};

export const getCategories = async () => {
  const { data } = await fetcher.get<CategoryInfo[]>(
    PRODUCT_API_PATH.categories
  );
  return data;
};

export const getProductDetail = async (productId: string) => {
  const { data } = await fetcher.get<ProductDetailInfo>(
    `${PRODUCT_API_PATH.products}/${productId}`
  );

  return data;
};

export const postProduct = async (productInfo: FormData) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  return await fetcher.post<{ productId: number }>(
    PRODUCT_API_PATH.products,
    productInfo,
    config
  );
};

export const patchProduct = async ({
  productId,
  productInfo,
}: {
  productId: string | undefined;
  productInfo: FormData;
}) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  return await fetcher.patch(
    `${PRODUCT_API_PATH.products}/${productId}`,
    productInfo,
    config
  );
};

export const deleteProduct = async (productId: string) => {
  return await fetcher.delete(`${PRODUCT_API_PATH.products}/${productId}`);
};

export const getStatuses = async () => {
  const { data } = await fetcher.get<Status[]>(PRODUCT_API_PATH.statuses);
  return data;
};

export const patchProductStatus = async ({
  productId,
  statusId,
}: {
  productId: string | undefined;
  statusId: number;
}) => {
  return await fetcher.patch(
    `${PRODUCT_API_PATH.products}/${productId}/status`,
    {
      statusId,
    }
  );
};

export const getProduct = async ({
  addressId,
  categoryId,
  cursor = 0,
  size = 10,
}: {
  addressId: number | null;
  categoryId: number | null;
  cursor: number | undefined;
  size?: number;
}) => {
  const baseUrl = PRODUCT_API_PATH.products;
  const pathVariable = `?addressId=${addressId}${
    categoryId ? `&categoryId=${categoryId}` : ""
  }&cursor=${cursor}&size=${size}
  `;

  const url = baseUrl + pathVariable;
  const { data } = await fetcher.get<ProductList>(url);

  return data;
};

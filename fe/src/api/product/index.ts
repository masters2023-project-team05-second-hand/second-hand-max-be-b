import { fetcher } from "@api/fetcher";
import {
  AddressList,
  CategoryInfo,
  ProductDetailInfo,
  ProductList,
  Status,
} from "@api/type";
import { PRODUCT_API_PATH } from "./constants";
import { ProductRegisterInfo } from "@components/ProductRegister/type";

export const getAddresses = async (
  page: number = 0,
  option?: {
    searchWord?: string;
    size?: number;
  }
) => {
  const baseUrl = PRODUCT_API_PATH.addresses;
  const pathVariable = `?page=${page}&size=${option?.size ?? 10}${
    option?.searchWord && `&search=${option.searchWord}`
  }`;
  const { data } = await fetcher.get<AddressList>(baseUrl + pathVariable);
  return data;
};

export const getCategories = async () => {
  const { data } = await fetcher.get<CategoryInfo[]>(
    PRODUCT_API_PATH.categories
  );
  return data;
};

export const getProductDetail = async (productId: number) => {
  const { data } = await fetcher.get<ProductDetailInfo>(
    `${PRODUCT_API_PATH.products}/${productId}`
  );

  return data;
};

export const postProduct = async (productInfo: ProductRegisterInfo) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  const formData = new FormData();
  const price = productInfo.price.replace(/,/g, "");

  productInfo.newImages?.forEach((image) => {
    formData.append("images", image.image);
  });
  formData.append("title", productInfo.title);
  formData.append("content", productInfo.content);
  formData.append("categoryId", JSON.stringify(productInfo.categoryId));
  formData.append("addressId", JSON.stringify(productInfo.address.id));
  formData.append("price", price);

  return await fetcher.post<{ productId: number }>(
    PRODUCT_API_PATH.products,
    formData,
    config
  );
};

export const patchProduct = async ({
  productId,
  productInfo,
}: {
  productId: number;
  productInfo: ProductRegisterInfo;
}) => {
  const config = {
    headers: {
      "content-type": "multipart/form-data",
    },
  };

  const formData = new FormData();
  const price = productInfo.price.replace(/,/g, "");

  productInfo.newImages?.forEach((image) => {
    formData.append("newImages", image.image);
  });
  if (productInfo.deletedImageIds?.length) {
    formData.append("deletedImageIds", productInfo.deletedImageIds?.join());
  }
  formData.append("title", productInfo.title);
  formData.append("content", productInfo.content);
  formData.append("categoryId", JSON.stringify(productInfo.categoryId));
  formData.append("addressId", JSON.stringify(productInfo.address.id));
  formData.append("price", price);

  return await fetcher.patch(
    `${PRODUCT_API_PATH.products}/${productId}`,
    formData,
    config
  );
};

export const deleteProduct = async (productId: number) => {
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
  productId: number;
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
  addressId: number;
  cursor: number;
  categoryId?: number;
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

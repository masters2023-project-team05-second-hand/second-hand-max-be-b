export type Tokens = {
  accessToken: string;
  refreshToken: string;
};

export type Member = {
  nickname: string;
  profileImgUrl: string;
};

export type UserAddressInfo = AddressInfo & {
  isLastVisited: boolean;
};

export type AddressInfo = {
  id: number;
  name: string;
};

export type CategoryInfo = {
  id: number;
  name: string;
  imgUrl: string;
};

export type AddressList = {
  addresses: AddressInfo[];
  hasNext: boolean;
};

export type ProductDetailInfo = {
  isSeller: boolean;
  product: {
    seller: string;
    category: CategoryInfo;
    address: AddressInfo;
    title: string;
    contents: string;
    price: number;
    createdTime: string;
    status: number;
  };
  images: {
    id: number;
    url: string;
  }[];
  stats: {
    chatCount: number;
    wishCount: number;
    viewCount: number;
  };
  statuses: {
    id: number;
    type: string;
  }[];
};

export type PostNewProduct = {
  images: File[];
  title: string;
  content: string;
  categoryId: number;
  addressId: number;
  price: number;
};

export type PatchProduct = {
  deletedImageIds: number[];
  newImages: File[];
  title: string;
  content: string;
  price: number;
  addressId: number;
  categoryId: number;
};

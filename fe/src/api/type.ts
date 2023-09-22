export type Tokens = {
  accessToken: string;
  refreshToken: string;
};

export type Member = {
  id: number;
  nickname: string;
  profileImgUrl: string;
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
  product: ProductInfo;
  images: ProductImages[];
  stats: ProductStats;
  statuses: {
    id: number;
    type: string;
  }[];
};

export type ProductStats = {
  chatCount: number;
  wishCount: number;
  viewCount: number;
};

export type ProductInfo = {
  seller: {
    id: number;
    nickname: string;
  };
  category: CategoryInfo;
  address: AddressInfo;
  title: string;
  contents: string;
  price: number;
  createdTime: string;
  status: number;
};

export type ProductImages = {
  id: number;
  url: string;
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

export type Status =
  | {
      id: 1;
      type: "판매중";
    }
  | {
      id: 2;
      type: "판매완료";
    }
  | {
      id: 3;
      type: "예약중";
    };

export type ProductItem = {
  sellerId: number;
  productId: number;
  thumbnailUrl: string;
  title: string;
  addressName: string;
  createdTime: string;
  price: number;
  statusId: number;
  stats: {
    chatCount: number;
    wishCount: number;
  };
};

export type ProductList = {
  products: ProductItem[];
  hasNext: boolean;
};

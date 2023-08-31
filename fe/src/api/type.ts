export type AuthInfo = {
  tokens: {
    accessToken: string;
    refreshToken: string;
  };
  addresses: UserAddressInfo[];
  member: Member;
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
  imageUrl: string;
};

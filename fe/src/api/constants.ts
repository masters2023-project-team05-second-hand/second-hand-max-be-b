import { ROUTE_PATH } from "@router/constants";

const {
  VITE_APP_API_URL,
  VITE_OAUTH_KAKAO_CLIENT_ID,
  VITE_OAUTH_GITHUB_CLIENT_ID,
} = import.meta.env;

export const API_PATH = {
  login: (provider: "kakao" | "github") => `/api/members/sign-in/${provider}`,
  logout: "/api/sign-out",
  categories: "/api/categories",
  refresh: "/api/reissue-access-token",
  member: "/api/members",
  userProfile: "/api/members/profile-image",
  productDetail: (productId: number) => `/api/products/${productId}`,
  memberAddress: "/api/members/addresses",
  addresses: (page: number, size: number) =>
    `/api/addresses?page=${page}&size=${size}`,
  nickname: "/api/members/nickname",
  newProduct: "/api/products",
  editProduct: (productId: number) => `/api/products/${productId}`,
};

export const BASE_API_URL = VITE_APP_API_URL;

export const KAKAO_LOGIN_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${VITE_OAUTH_KAKAO_CLIENT_ID}&redirect_uri=${BASE_API_URL}${ROUTE_PATH.auth.kakao}&response_type=code`;

export const GITHUB_LOGIN_URL = `https://github.com/login/oauth/authorize?client_id=${VITE_OAUTH_GITHUB_CLIENT_ID}&redirect_uri=${BASE_API_URL}${ROUTE_PATH.auth.github}`;

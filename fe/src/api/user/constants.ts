import { ROUTE_PATH } from "@router/constants";

const {
  VITE_APP_API_URL,
  VITE_OAUTH_KAKAO_CLIENT_ID,
  VITE_OAUTH_GITHUB_CLIENT_ID,
} = import.meta.env;

export const USER_API_PATH = {
  login: (provider: "kakao" | "github") => `/api/members/sign-in/${provider}`,
  logout: "/api/sign-out",
  refresh: "/api/reissue-access-token",
  member: "/api/members",
  userProfile: "/api/members/profile-image",
  memberAddress: "/api/members/addresses",
  nickname: "/api/members/nickname",
  wishlist: "/api/members/wishlist",
  sales: "/api/members/sales",
};

export const KAKAO_LOGIN_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${VITE_OAUTH_KAKAO_CLIENT_ID}&redirect_uri=${VITE_APP_API_URL}${ROUTE_PATH.auth.kakao}&response_type=code`;
export const GITHUB_LOGIN_URL = `https://github.com/login/oauth/authorize?client_id=${VITE_OAUTH_GITHUB_CLIENT_ID}&redirect_uri=${VITE_APP_API_URL}${ROUTE_PATH.auth.github}`;

export const ENTIRE_STATUS_ID = [1, 2, 3];

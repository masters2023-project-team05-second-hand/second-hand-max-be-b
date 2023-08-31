import { API_PATH } from "./constants";
import { fetcher } from "./fetcher";
import { AuthInfo } from "./type";

export const postSocialLogin = async (
  provider: "kakao" | "github",
  body: { accessCode: string }
) => {
  return await fetcher.post<AuthInfo>(API_PATH.login(provider), body);
};

export const postLogout = async (body: { refreshToken: string }) => {
  return await fetcher.post(API_PATH.logout, body);
};

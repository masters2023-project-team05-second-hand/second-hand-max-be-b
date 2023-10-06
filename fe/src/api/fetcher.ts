import { ROUTE_PATH } from "@router/constants";
import { postRefreshToken } from "api/user";
import axios, { AxiosError } from "axios";
import { BASE_API_URL, ERROR_CODE } from "./constants";
import { USER_API_PATH } from "./user/constants";

export const fetcher = axios.create({
  baseURL: BASE_API_URL,
  headers: { "Content-Type": "application/json" },
});

fetcher.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

fetcher.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // TODO: 토큰 만료에 대한 백엔드 에러코드 통일 요청 후 개선하기
    if (
      error.response &&
      ERROR_CODE.UNAUTHORIZED.includes(error.response.status)
    ) {
      try {
        const originalRequest = error.config;

        if (originalRequest && originalRequest.url !== USER_API_PATH.refresh) {
          return refreshAccessToken().then(() => fetcher(originalRequest));
        }

        return Promise.reject();
      } catch {
        console.error("error", "네트워크 오류가 발생했습니다.");
        return Promise.reject();
      }
    }
  }
);

const refreshAccessToken = async () => {
  const refreshToken = localStorage.getItem("refreshToken");

  if (!refreshToken) {
    return Promise.reject();
  }

  // TODO: 로그아웃 중복 로직 제거하기 (함수로 빼기)
  try {
    const { data } = await postRefreshToken(refreshToken);
    localStorage.setItem("accessToken", data.accessToken);
  } catch {
    localStorage.removeItem("expirationTime");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    window.location.href = ROUTE_PATH.account;

    return Promise.reject();
  }
};

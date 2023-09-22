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

// TODO: refresh token이 만료되었을 때, refresh token을 재발급 받고 다시 요청을 보내는 로직 구현 (작동하는지 테스트 필요)
fetcher.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response?.status !== ERROR_CODE.UNAUTHORIZED) {
      return Promise.reject(error);
    }

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
);

const refreshAccessToken = async () => {
  const refreshToken = localStorage.getItem("refreshToken");
  if (!refreshToken) {
    return Promise.reject();
  }

  try {
    const { data } = await postRefreshToken(refreshToken);
    localStorage.setItem("accessToken", data.accessToken);
  } catch {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    return Promise.reject();
  }
};

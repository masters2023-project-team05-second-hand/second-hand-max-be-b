import axios from "axios";
import { BASE_API_URL } from "./constants";

export const fetcher = axios.create({
  baseURL: `${BASE_API_URL}`,
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

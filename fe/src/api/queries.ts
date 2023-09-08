import { useInfiniteQuery, useQueries, useQuery } from "@tanstack/react-query";
import {
  getAddresses,
  getCategories,
  getMember,
  getMemberAddress,
  getProductDetail,
} from ".";

export const useAddressesInfiniteQuery = (option?: { size: number }) => {
  return useInfiniteQuery({
    queryKey: ["addresses"],
    queryFn: ({ pageParam }) => getAddresses(pageParam, option?.size),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

export const useUserInfoQuery = (
  { enabled }: { enabled: boolean } = { enabled: true }
) => {
  return useQueries({
    queries: [
      {
        queryKey: ["getMember"],
        queryFn: getMember,
        enabled,
        staleTime: Infinity,
      },
      {
        queryKey: ["getMemberAddresses"],
        queryFn: getMemberAddress,
        enabled,
        staleTime: Infinity,
      },
    ],
  });
};

export const useCategoryQuery = () => {
  return useQuery({
    queryKey: ["getCategories"],
    queryFn: getCategories,
    staleTime: Infinity,
  });
};

export const useProductDetailQuery = (id: number, enabled: boolean) => {
  return useQuery({
    queryKey: ["getProductDetail"],
    queryFn: () => getProductDetail(id),
    enabled,
    staleTime: Infinity,
  });
};

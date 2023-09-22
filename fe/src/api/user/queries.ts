import { useToast } from "@hooks/useToast";
import {
  useInfiniteQuery,
  useMutation,
  useQueries,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import {
  getMember,
  getMemberAddress,
  getProductLikeStatus,
  getUserSalesProduct,
  getUserWishlistCategory,
  getUserWishlistProduct,
  postProductLike,
} from ".";

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

export const useGetProductLikeQuery = (productId: string) => {
  return useQuery({
    queryKey: ["getProductLikeStatus", productId],
    queryFn: () => getProductLikeStatus(productId),
    staleTime: Infinity,
  });
};

export const useMutateProductLike = ({
  productId,
  onError,
}: {
  productId: string;
  onError: () => void;
}) => {
  const queryClient = useQueryClient();
  const { toast } = useToast();

  // TODO: response에 추가인지 삭제인지 받기
  return useMutation(postProductLike, {
    onSuccess: () => {
      toast({
        type: "success",
        title: "관심 상품 변경 완료",
        message: "관심 상품 변경이 완료되었습니다.",
      });
    },
    onError: () => {
      toast({
        type: "error",
        title: "관심 상품 변경 실패",
        message: "관심 상품 변경에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
      onError();
    },
    onSettled: () => {
      queryClient.invalidateQueries({
        queryKey: ["getProductLikeStatus", productId],
      });
      queryClient.invalidateQueries({
        queryKey: ["getUserWishlistCategory"],
      });
    },
  });
};

export const useUserLikeCategories = () => {
  return useQuery({
    queryKey: ["getUserWishlistCategory"],
    queryFn: getUserWishlistCategory,
    staleTime: Infinity,
  });
};

export const useUserWishlistInfiniteQuery = (categoryId: number) => {
  return useInfiniteQuery({
    queryKey: ["getUserWishlistProduct", categoryId],
    queryFn: ({ pageParam }) =>
      getUserWishlistProduct({
        categoryId,
        page: pageParam,
        size: 10,
      }),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

export const useUserSalesInfiniteQuery = (statusId: number) => {
  return useInfiniteQuery({
    queryKey: ["getUserSalesProduct", statusId],
    queryFn: ({ pageParam }) =>
      getUserSalesProduct({
        statusId,
        page: pageParam,
        size: 10,
      }),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

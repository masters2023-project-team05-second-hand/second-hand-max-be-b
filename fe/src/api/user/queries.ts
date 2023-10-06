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
import { userKeys } from "./../queryKeys";
import { Status } from "@api/type";

export const useUserInfoQuery = (
  { enabled }: { enabled: boolean } = { enabled: true }
) => {
  return useQueries({
    queries: [
      {
        ...userKeys.member,
        queryFn: getMember,
        enabled,
        staleTime: Infinity,
      },
      {
        ...userKeys.memberAddresses,
        queryFn: getMemberAddress,
        enabled,
        staleTime: Infinity,
      },
    ],
  });
};

export const useGetProductLikeQuery = (productId: number) => {
  return useQuery({
    ...userKeys.productLikeStatus(productId),
    queryFn: () => getProductLikeStatus(productId),
    staleTime: Infinity,
  });
};

export const useMutateProductLike = ({
  productId,
  onError,
}: {
  productId: number;
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
      queryClient.invalidateQueries(userKeys.wishlistCategory);
    },
    onError: () => {
      toast({
        type: "error",
        title: "관심 상품 변경 실패",
        message: "관심 상품 변경에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
      onError();
    },
    // TODO: 개선 필요
    onSettled: () => {
      queryClient.invalidateQueries(userKeys.productLikeStatus(productId));
    },
  });
};

export const useUserLikeCategories = () => {
  return useQuery({
    ...userKeys.wishlistCategory,
    queryFn: getUserWishlistCategory,
    staleTime: Infinity,
  });
};

export const useUserWishlistInfiniteQuery = (categoryId: number) => {
  return useInfiniteQuery({
    ...userKeys.wishlistProduct(categoryId),
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

export const useUserSalesInfiniteQuery = (
  statuses: Status[],
  statusId: number
) => {
  return useInfiniteQuery({
    ...userKeys.salesProduct(statusId),
    queryFn: ({ pageParam }) =>
      getUserSalesProduct({
        statuses,
        statusId,
        page: pageParam,
        size: 10,
      }),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

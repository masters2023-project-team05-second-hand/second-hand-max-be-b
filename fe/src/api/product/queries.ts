import { useToast } from "@hooks/useToast";
import {
  useInfiniteQuery,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import {
  deleteProduct,
  getAddresses,
  getCategories,
  getProduct,
  getProductDetail,
  getStatuses,
  patchProductStatus,
} from ".";

export const useAddressesInfiniteQuery = (option?: { size: number }) => {
  return useInfiniteQuery({
    queryKey: ["addresses"],
    queryFn: ({ pageParam }) => getAddresses(pageParam, option?.size),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

export const useCategoryQuery = () => {
  return useQuery({
    queryKey: ["getCategories"],
    queryFn: getCategories,
    staleTime: Infinity,
  });
};

export const useProductDetailQuery = (id: string, enabled: boolean) => {
  return useQuery({
    queryKey: ["getProductDetail", id],
    queryFn: () => getProductDetail(id),
    enabled,
    staleTime: Infinity,
  });
};

export const useDeleteProductQuery = ({
  productId,
  onSuccess,
  invalidateQueryKey,
}: {
  productId: string;
  onSuccess?: () => void;
  invalidateQueryKey?: string[];
}) => {
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const deleteProductMutation = useMutation(deleteProduct);

  const onDeleteProduct = () => {
    deleteProductMutation.mutate(productId, {
      onSuccess: () => {
        toast({
          type: "success",
          title: "상품 삭제 성공",
          message: "상품 삭제에 성공했습니다.",
        });
        onSuccess?.();
        queryClient.invalidateQueries({ queryKey: invalidateQueryKey });
      },
      onError: () => {
        toast({
          type: "error",
          title: "상품 삭제 실패",
          message: "상품 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.",
        });
      },
    });
  };

  return { onDeleteProduct };
};

export const useProductStatusesQuery = () => {
  return useQuery({
    queryKey: ["getStatuses"],
    queryFn: () => getStatuses(),
    staleTime: Infinity,
  });
};

export const useMutateProductStatus = ({
  onSettled,
  invalidateQueryKey,
}: {
  onSettled?: () => void;
  invalidateQueryKey?: string[];
}) => {
  const queryClient = useQueryClient();
  const { toast } = useToast();

  return useMutation(patchProductStatus, {
    onSuccess: () => {
      toast({
        type: "success",
        title: "상태 변경 완료",
        message: "상태 변경이 완료되었습니다.",
      });
      queryClient.invalidateQueries({ queryKey: invalidateQueryKey });
    },
    onError: () => {
      toast({
        type: "error",
        title: "상태 변경 실패",
        message: "상태 변경에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
    },
    onSettled,
  });
};

export const useGetProductListInfiniteQuery = ({
  addressId,
  categoryId,
  size,
}: {
  addressId: number | null;
  categoryId: number | null;
  size?: number;
}) => {
  return useInfiniteQuery({
    queryKey: ["getProduct", addressId, categoryId, size],
    queryFn: ({ pageParam }) =>
      getProduct({ addressId, categoryId, cursor: pageParam, size }),
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext
        ? lastPage.products[lastPage.products.length - 1].productId
        : undefined;
    },
  });
};

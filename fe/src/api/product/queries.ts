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
  patchProduct,
  patchProductStatus,
  postProduct,
} from ".";
import { productKeys } from "./../queryKeys";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "@router/constants";

export const useAddressesInfiniteQuery = (option?: {
  searchWord?: string;
  size?: number;
}) => {
  return useInfiniteQuery({
    ...productKeys.addresses(option?.searchWord),
    queryFn: ({ pageParam }) => getAddresses(pageParam, option),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasNext ? allPages.length : undefined,
  });
};

export const useCategoryQuery = () => {
  return useQuery({
    ...productKeys.categories,
    queryFn: getCategories,
    staleTime: Infinity,
  });
};

export const useProductDetailQuery = (id: number, enabled: boolean) => {
  return useQuery({
    ...productKeys.detail(id),
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
  productId: number;
  onSuccess?: () => void;
  invalidateQueryKey?: readonly unknown[];
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
    ...productKeys.statuses,
    queryFn: () => getStatuses(),
    staleTime: Infinity,
  });
};

export const useMutateProductStatus = ({
  onSettled,
  invalidateQueryKey,
}: {
  onSettled?: () => void;
  invalidateQueryKey?: readonly unknown[];
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
      invalidateQueryKey &&
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
  addressId: number;
  categoryId?: number;
  size?: number;
}) => {
  return useInfiniteQuery({
    ...productKeys.products(addressId, categoryId, size),
    queryFn: ({ pageParam }) =>
      getProduct({ addressId, categoryId, cursor: pageParam, size }),
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext
        ? lastPage.products[lastPage.products.length - 1].productId
        : undefined;
    },
  });
};

export const useMutateNewProduct = () => {
  const navigate = useNavigate();
  const { toast } = useToast();

  return useMutation(postProduct, {
    onSuccess: (res) => {
      navigate(`${ROUTE_PATH.detail}/${res.data.productId}`, {
        state: { prevRoute: ROUTE_PATH.home },
      });
    },
    onError: () => {
      toast({
        type: "error",
        title: "상품 등록 실패",
        message: "상품 등록에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
    },
  });
};

export const useMutatePatchProduct = ({
  productId,
  invalidateQueryKey,
}: {
  productId: number;
  invalidateQueryKey?: readonly unknown[];
}) => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { toast } = useToast();

  return useMutation(patchProduct, {
    onSuccess: () => {
      navigate(`${ROUTE_PATH.detail}/${productId}`, {
        state: { prevRoute: ROUTE_PATH.home },
      });
      queryClient.invalidateQueries(invalidateQueryKey);
    },
    onError: () => {
      toast({
        type: "error",
        title: "상품 수정 실패",
        message: "상품 수정에 실패했습니다. 잠시 후 다시 시도해주세요.",
      });
    },
  });
};

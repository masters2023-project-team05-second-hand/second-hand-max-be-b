import { createQueryKeys } from "@lukemorales/query-key-factory";

export const productKeys = createQueryKeys("product", {
  addresses: (searchWord?: string) => ({
    queryKey: ["getAddresses", searchWord],
  }),
  categories: { queryKey: ["getCategories"] },
  detail: (id: number) => ({ queryKey: ["getProductDetail", id] }),
  statuses: { queryKey: ["getStatuses"] },
  products: (addressId?: number, categoryId?: number, size?: number) => ({
    queryKey: ["getProduct", addressId, categoryId, size],
  }),
});

export const userKeys = createQueryKeys("user", {
  socialLogin: { queryKey: ["socialLogin"] },
  member: { queryKey: ["getMember"] },
  memberAddresses: { queryKey: ["getMemberAddresses"] },
  productLikeStatus: (productId: number) => ({
    queryKey: ["getProductLikeStatus", productId],
  }),
  wishlistCategory: { queryKey: ["getUserWishlistCategory"] },
  wishlistProduct: (categoryId: number) => ({
    queryKey: ["getUserWishlistProduct", categoryId],
  }),
  salesProduct: (statusId: number) => ({
    queryKey: ["getUserSalesProduct", statusId],
  }),
});

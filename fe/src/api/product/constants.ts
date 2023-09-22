export const PRODUCT_API_PATH = {
  categories: "/api/categories",
  addresses: (page: number, size: number) =>
    `/api/addresses?page=${page}&size=${size}`,
  products: "/api/products",
  statuses: "/api/statuses",
};

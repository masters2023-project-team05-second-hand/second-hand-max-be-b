export const ROUTE_PATH = {
  home: "/",
  sales: "/sales",
  wish: "/wish",
  chat: "/chat",
  account: {
    index: "/account",
    login: "/account/login",
    setting: "/account/setting",
    auth: {
      index: "/account/auth/:provider",
      kakao: "/account/auth/kakao",
      github: "/account/auth/github",
    },
  },
  register: "/register",
  new: "/new",
  category: "/category",
  product: "/product/:id",
};

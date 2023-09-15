import { Auth } from "@pages/Auth";
import Category from "@pages/Category";
import Chat from "@pages/Chat";
import MyAccount from "@pages/MyAccount/MyAccount";
import ProductDetail from "@pages/ProductDetail";
import ProductList from "@pages/ProductList";
import ProductRegister from "@pages/ProductRegister";
import Register from "@pages/Register";
import SalesList from "@pages/SalesList";
import WishList from "@pages/WishList";
import UserProvider from "@router/UserProvider";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import ProtectedRoutes from "./ProtectedRoutes";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.home} element={<UserProvider />}>
        <Route index element={<ProductList />} />
        <Route element={<ProtectedRoutes />}>
          <Route path={ROUTE_PATH.new} element={<ProductRegister />} />
          <Route
            path={`${ROUTE_PATH.edit}/:productId`}
            element={<ProductRegister />}
          />
          <Route path={ROUTE_PATH.category} element={<Category />} />
          <Route
            path={`${ROUTE_PATH.detail}/:productId`}
            element={<ProductDetail />}
          />
          <Route path={ROUTE_PATH.sales} element={<SalesList />} />
          <Route path={ROUTE_PATH.wish} element={<WishList />} />
          <Route path={ROUTE_PATH.chat} element={<Chat />} />
        </Route>
        <Route path={ROUTE_PATH.account} element={<MyAccount />} />
        <Route path={ROUTE_PATH.auth.index} element={<Auth />} />
      </Route>
      <Route path={ROUTE_PATH.register} element={<Register />} />
    </>
  )
);

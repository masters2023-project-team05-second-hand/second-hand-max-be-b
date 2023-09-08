import { Auth } from "@pages/Auth";
import Chat from "@pages/Chat";
import Category from "@pages/Category";
import Home from "@pages/Home";
import MyAccount from "@pages/MyAccount/MyAccount";
import ProductRegister from "@pages/ProductRegister";
import ProductDetail from "@pages/ProductDetail";
import Register from "@pages/Register";
import SalesList from "@pages/SalesList";
import WishList from "@pages/WishList";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.home} element={<Home />} />
      <Route path={ROUTE_PATH.new} element={<ProductRegister />} />
      <Route path={ROUTE_PATH.edit} element={<ProductRegister />} />
      <Route path={ROUTE_PATH.category} element={<Category />} />
      <Route path={ROUTE_PATH.detail} element={<ProductDetail />} />
      <Route path={ROUTE_PATH.sales} element={<SalesList />} />
      <Route path={ROUTE_PATH.wish} element={<WishList />} />
      <Route path={ROUTE_PATH.chat} element={<Chat />} />
      <Route path={ROUTE_PATH.account} element={<MyAccount />} />
      <Route path={ROUTE_PATH.auth.index} element={<Auth />} />
      <Route path={ROUTE_PATH.register} element={<Register />} />
    </>
  )
);

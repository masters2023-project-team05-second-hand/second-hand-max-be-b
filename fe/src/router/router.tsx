import Chat from "@pages/Chat";
import Category from "@pages/Home/Category";
import Home from "@pages/Home/Home";
import NewProduct from "@pages/Home/NewProduct";
import { Auth } from "@pages/MyAccount/Auth";
import Login from "@pages/MyAccount/Login";
import MyAccount from "@pages/MyAccount/MyAccount";
import Register from "@pages/MyAccount/Register";
import Setting from "@pages/MyAccount/Setting";
import Product from "@pages/Product";
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
      <Route path={ROUTE_PATH.new} element={<NewProduct />} />
      <Route path={ROUTE_PATH.category} element={<Category />} />
      <Route path={ROUTE_PATH.product} element={<Product />} />
      <Route path={ROUTE_PATH.sales} element={<SalesList />} />
      <Route path={ROUTE_PATH.wish} element={<WishList />} />
      <Route path={ROUTE_PATH.chat} element={<Chat />} />
      <Route path={ROUTE_PATH.account.index} element={<MyAccount />}>
        <Route path={ROUTE_PATH.account.auth.index} element={<Auth />} />
        <Route path={ROUTE_PATH.account.login} element={<Login />} />
        <Route path={ROUTE_PATH.account.setting} element={<Setting />} />
      </Route>
      <Route path={ROUTE_PATH.register} element={<Register />} />
    </>
  )
);

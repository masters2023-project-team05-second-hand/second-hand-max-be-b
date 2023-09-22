import { Navigate, Outlet } from "react-router-dom";
import { useIsLoginValue } from "store";
import { ROUTE_PATH } from "./constants";

export default function ProtectedRoutes() {
  const isLogin = useIsLoginValue();

  return isLogin ? <Outlet /> : <Navigate to={ROUTE_PATH.account} />;
}

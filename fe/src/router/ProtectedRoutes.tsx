import { Navigate, Outlet } from "react-router-dom";
import { useAddressListValue } from "store";
import { ROUTE_PATH } from "./constants";

export default function ProtectedRoutes() {
  const addresses = useAddressListValue();

  return addresses.length ? <Outlet /> : <Navigate to={ROUTE_PATH.register} />;
}

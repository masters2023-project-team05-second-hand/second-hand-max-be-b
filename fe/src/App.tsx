import { router } from "@router/router";
import GlobalStyle from "@styles/GlobalStyle";
import { theme } from "@styles/designSystem";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useSetAtom } from "jotai";
import { RouterProvider } from "react-router-dom";
import { addressListAtom, memberAtom, tokensAtom } from "store";
import styled, { ThemeProvider } from "styled-components";

const queryClient = new QueryClient();

export default function App() {
  const member = JSON.parse(localStorage.getItem("member") || "{}");
  const tokens = {
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
  };
  const addresses = JSON.parse(localStorage.getItem("addresses") || "[]");

  const setMember = useSetAtom(memberAtom);
  const setTokens = useSetAtom(tokensAtom);
  const setAddresses = useSetAtom(addressListAtom);

  setMember(member);
  setTokens(tokens);
  setAddresses(addresses);

  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
      <ModalRoot id="modal-root" />
    </ThemeProvider>
  );
}

const ModalRoot = styled.div`
  position: fixed;
  top: 0;
  z-index: 100;
`;

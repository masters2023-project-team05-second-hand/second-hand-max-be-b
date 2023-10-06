import gajiBackground from "@assets/image/gaji.png";
import Toaster from "@components/common/Toaster";
import GlobalStateProvider from "@router/GlobalStateProvider";
import UserProvider from "@router/UserProvider";
import { router } from "@router/router";
import GlobalStyle from "@styles/GlobalStyle";
import { theme } from "@styles/designSystem";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { RouterProvider } from "react-router-dom";
import styled, { ThemeProvider } from "styled-components";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={false} />
        <StyledApp>
          <UserProvider />
          <GlobalStateProvider />
          <RouterProvider router={router} />
        </StyledApp>
      </QueryClientProvider>
      <ModalRoot id="modal-root" />
      <Toaster />
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  box-sizing: border-box;
  background: url(${gajiBackground}) repeat center;
  background-size: 300px;
`;

const ModalRoot = styled.div`
  position: fixed;
  top: 0;
  z-index: 100;
`;

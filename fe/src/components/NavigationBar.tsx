import { ReactComponent as HeartIcon } from "@assets/icon/heart.svg";
import { ReactComponent as HomeIcon } from "@assets/icon/home.svg";
import { ReactComponent as MessageIcon } from "@assets/icon/message.svg";
import { ReactComponent as NewsIcon } from "@assets/icon/news.svg";
import { ReactComponent as UserIcon } from "@assets/icon/user-circle.svg";
import { ROUTE_PATH } from "@router/constants";
import { HEIGHT, WIDTH } from "@styles/constants";
import { NavLink } from "react-router-dom";
import styled from "styled-components";

export default function NavigationBar() {
  return (
    <StyledNavigationBar>
      {NAVIGATION.map(({ icon, value, path }) => (
        <NavigationItem key={path}>
          <NavLink
            to={path}
            className={({ isActive }) => (isActive ? "active" : "")}>
            {icon}
            <span>{value}</span>
          </NavLink>
        </NavigationItem>
      ))}
    </StyledNavigationBar>
  );
}

const NAVIGATION = [
  {
    icon: <HomeIcon />,
    value: "홈화면",
    path: ROUTE_PATH.home,
  },
  {
    icon: <NewsIcon />,
    value: "판매내역",
    path: ROUTE_PATH.sales,
  },
  {
    icon: <HeartIcon />,
    value: "관심상품",
    path: ROUTE_PATH.wish,
  },
  {
    icon: <MessageIcon />,
    value: "채팅",
    path: ROUTE_PATH.chat,
  },
  {
    icon: <UserIcon />,
    value: "내 계정",
    path: ROUTE_PATH.account,
  },
];

const StyledNavigationBar = styled.ul`
  width: ${WIDTH.app}px;
  position: fixed;
  bottom: 0;
  display: flex;
  height: ${HEIGHT.navigationBar}px;
  justify-content: space-around;
  align-items: center;
  border-top: 0.8px solid ${({ theme: { color } }) => color.neutralBorder};
  background-color: ${({ theme: { color } }) => color.neutralBackground};

  .active {
    color: ${({ theme: { color } }) => color.neutralTextStrong};
    font: ${({ theme: { font } }) => font.availableStrong10};

    svg {
      filter: none;
    }
  }
`;

const NavigationItem = styled.li`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  width: 48px;
  height: 48px;
  color: ${({ theme: { color } }) => color.neutralTextWeak};
  font: ${({ theme: { font } }) => font.availableStrong10};

  svg {
    filter: ${({ theme: { filter } }) => filter.neutralTextWeak};
  }

  a {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
  }
`;

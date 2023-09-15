import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { HEIGHT, WIDTH } from "@styles/constants";

export default function ProductListFAB() {
  const navigate = useNavigate();

  const goToAddProduct = () => {
    navigate(ROUTE_PATH.new);
  };

  return (
    <FAB>
      <Button
        size={{ width: 56, height: 56 }}
        color="accentText"
        backgroundColor="accentPrimary"
        radius="half"
        leftIcon={<PlusIcon />}
        onClick={goToAddProduct}
      />
    </FAB>
  );
}

const FAB = styled.div`
  width: ${WIDTH.FAB}px;
  position: fixed;
  left: calc(50% + ((${WIDTH.page}px) / 2) - (${WIDTH.FAB}px * 1.5));
  bottom: calc(${HEIGHT.navigationBar}px + (${WIDTH.FAB}px) / 2);
`;

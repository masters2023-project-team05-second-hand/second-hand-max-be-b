import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import Button from "@components/common/Buttons/Button";
import { useLocation, useNavigate } from "react-router-dom";

export default function BackButton() {
  const location = useLocation();
  const navigate = useNavigate();

  const goBack = () => navigate(location.state?.prevRoute ?? -1);

  return (
    <Button
      value="뒤로"
      color="accentText"
      fontName="availableStrong16"
      onClick={goBack}
      leftIcon={<ChevronLeftIcon />}
    />
  );
}

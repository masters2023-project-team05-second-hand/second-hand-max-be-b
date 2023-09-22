import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import Button from "@components/common/Buttons/Button";
import { ColorName } from "@styles/designSystem";

export default function BackButton({
  color,
  onClick,
}: {
  color: ColorName;
  onClick: () => void;
}) {
  return (
    <Button
      value="뒤로"
      color={color}
      fontName="availableStrong16"
      onClick={onClick}
      leftIcon={<ChevronLeftIcon />}
    />
  );
}

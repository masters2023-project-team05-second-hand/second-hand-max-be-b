import { ReactNode, useState } from "react";
import Alert from "./Alert";

type AlertIndicatorProps = {
  button: ReactNode;
  message: string;
  onDeleteClick: () => void;
};

export default function AlertIndicator({
  button,
  message,
  onDeleteClick,
}: AlertIndicatorProps) {
  const [isAlertOpen, setIsAlertOpen] = useState(false);

  const toggleAlert = () => {
    setIsAlertOpen((prev) => !prev);
  };

  return (
    <>
      <div onClick={toggleAlert}>{button}</div>
      {isAlertOpen && (
        <Alert
          message={message}
          closeAlertHandler={toggleAlert}
          onDeleteClick={onDeleteClick}
        />
      )}
    </>
  );
}

import ChatMoreButton from "@components/ChatRoom/ChatMoreButton";
import TopBar from "@components/TopBar";
import BackButton from "@components/common/Buttons/BackButton";
import { useNavigate } from "react-router-dom";

export default function ChatRoomTopBar({
  roomId,
  partnerName,
}: {
  roomId?: string;
  partnerName: string;
}) {
  const navigate = useNavigate();

  const goBack = () => {
    navigate(-1);
  };

  return (
    <TopBar
      title={partnerName}
      backgroundColor="neutralBackgroundBlur"
      isWithBorder={true}
      leftBtn={<BackButton color="neutralText" onClick={goBack} />}
      rightBtn={<ChatMoreButton roomId={roomId} />}
    />
  );
}

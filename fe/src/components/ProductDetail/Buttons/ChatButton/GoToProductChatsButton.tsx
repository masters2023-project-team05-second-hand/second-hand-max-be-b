import Button from "@components/common/Buttons/Button";
import { useToast } from "@hooks/useToast";
import { ROUTE_PATH } from "@router/constants";
import { useNavigate } from "react-router-dom";

export function GoToProductChatsButton({
  chatCount,
  productId,
}: {
  chatCount: number;
  productId: number;
}) {
  const { toast } = useToast();
  const navigate = useNavigate();

  const showNoChatPartner = () => {
    toast({
      type: "info",
      title: "상품 채팅 목록 조회 완료",
      message: "채팅한 이웃이 없습니다",
    });
  };

  const moveToProductChats = () => {
    navigate(`${ROUTE_PATH.chat}/${productId}`);
  };

  const hasChat = chatCount && chatCount > 0;
  const chatRoomText = hasChat
    ? `대화 중인 채팅방 (${chatCount})`
    : "대화 중인 채팅방";
  const onClick = hasChat ? moveToProductChats : showNoChatPartner;

  return (
    <Button
      size={{ width: 115, height: 32 }}
      color="accentText"
      backgroundColor="accentPrimary"
      radius={8}
      fontName="availableStrong12"
      value={chatRoomText}
      onClick={onClick}
    />
  );
}

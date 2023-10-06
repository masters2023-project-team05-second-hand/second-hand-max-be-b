import { useGetChatRoomId } from "@api/chat/queries";
import Button from "@components/common/Buttons/Button";
import { ROUTE_PATH } from "@router/constants";
import { useNavigate } from "react-router-dom";
import { ChatInfo } from "./ChatButton";

export function GoToChatRoomButton({
  product,
  seller,
}: {
  product: ChatInfo["product"];
  seller: ChatInfo["seller"];
}) {
  const navigate = useNavigate();
  const moveToChatRoom = (chatRoomId: string) => {
    const hasChatRoom = !!chatRoomId;
    const path = hasChatRoom
      ? `${ROUTE_PATH.chatting}/${chatRoomId}`
      : ROUTE_PATH.chatting;

    navigate(path, {
      state: {
        product,
        partner: seller,
      },
    });
  };

  const { onGetChatRoomId } = useGetChatRoomId({
    productId: product.productId,
    onSuccess: moveToChatRoom,
  });

  return (
    <Button
      size={{ width: 115, height: 32 }}
      color="accentText"
      backgroundColor="accentPrimary"
      radius={8}
      fontName="availableStrong12"
      value="채팅하기"
      onClick={onGetChatRoomId}
    />
  );
}

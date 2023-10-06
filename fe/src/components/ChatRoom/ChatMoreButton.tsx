import { useDeleteChatRoom } from "@api/chat/queries";
import { ChatRoomLocationState } from "@api/type";
import { ReactComponent as DotsIcon } from "@assets/icon/dots.svg";
import Alert from "@components/common/Alert/Alert";
import Button from "@components/common/Buttons/Button";
import MenuIndicator from "@components/common/Menu/MenuIndicator";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

export default function ChatMoreButton({ roomId }: { roomId?: string }) {
  const [isLeaveAlertOpen, setIsLeaveAlertOpen] = useState(false);
  const navigate = useNavigate();
  const {
    state: { product },
  }: { state: ChatRoomLocationState } = useLocation();

  const { onDeleteChatRoom } = useDeleteChatRoom({
    productId: product.productId,
  });

  const openLeaveAlert = () => setIsLeaveAlertOpen(true);
  const closeLeaveAlert = () => setIsLeaveAlertOpen(false);

  const MoreMenuItemList = [
    {
      name: "알람 끄기",
      onClick: () => console.log("알람 끄기"),
    },
    { name: "신고하기", onClick: () => console.log("신고하기") },
    { name: "채팅방 나가기", onClick: openLeaveAlert, isWarning: true },
  ];

  const leaveChatRoom = () => {
    roomId && onDeleteChatRoom(roomId);
    navigate(-1);
  };

  return (
    <>
      <MenuIndicator itemList={MoreMenuItemList}>
        <Button color="neutralText" leftIcon={<DotsIcon />} />
      </MenuIndicator>
      {isLeaveAlertOpen && (
        <Alert
          message={
            "채팅방을 나가면 채팅 목록 및 대화 내용이 삭제되고 복구할 수 없습니다. 채팅방에서 나가시겠어요?"
          }
          onDeleteClick={leaveChatRoom}
          closeAlertHandler={closeLeaveAlert}
        />
      )}
    </>
  );
}

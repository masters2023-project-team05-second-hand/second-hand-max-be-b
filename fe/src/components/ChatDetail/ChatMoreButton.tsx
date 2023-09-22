import { ReactComponent as DotsIcon } from "@assets/icon/dots.svg";
import Alert from "@components/common/Alert/Alert";
import Button from "@components/common/Buttons/Button";
import MenuIndicator from "@components/common/Menu/MenuIndicator";
import { useState } from "react";

export default function ChatMoreButton() {
  const [isLeaveAlertOpen, setIsLeaveAlertOpen] = useState(false);

  const openLeaveAlert = () => setIsLeaveAlertOpen(true);
  const closeLeaveAlert = () => setIsLeaveAlertOpen(false);

  const MoreMenuItemList = [
    {
      name: "게시글 수정",
      onClick: () => console.log("게시글 수정"),
    },
    { name: "신고하기", onClick: () => console.log("신고하기") },
    { name: "채팅방 나가기", onClick: openLeaveAlert, isWarning: true },
  ];

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
          onDeleteClick={() => console.log("채팅방 나가기")}
          closeAlertHandler={closeLeaveAlert}
        />
      )}
    </>
  );
}

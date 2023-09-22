import Button from "@components/common/Buttons/Button";

type ChatButtonProps = {
  onClick: () => void;
  isSeller: boolean;
  chatCount?: number;
};

export default function ChatButton({
  onClick,
  isSeller,
  chatCount,
}: ChatButtonProps) {
  const isThereChatList = chatCount && chatCount > 0;
  const sellerMessage = isThereChatList
    ? `대화 중인 채팅방 (${chatCount})`
    : "대화 중인 채팅방";
  const buyerMessage = "채팅하기";

  return (
    <Button
      size={{ width: 115, height: 32 }}
      color="accentText"
      backgroundColor="accentPrimary"
      radius={8}
      fontName="availableStrong12"
      value={isSeller ? sellerMessage : buyerMessage}
      onClick={onClick}
    />
  );
}

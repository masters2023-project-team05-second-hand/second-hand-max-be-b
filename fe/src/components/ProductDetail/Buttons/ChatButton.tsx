import Button from "@components/common/Buttons/Button";

export default function ChatButton() {
  return (
    <Button
      size={{ width: 115, height: 32 }}
      color="accentText"
      backgroundColor="accentPrimary"
      radius={8}
      fontName="availableStrong12"
      // TODO: 채팅방 개수 표시
      value={"대화 중인 채팅방"}
    />
  );
}

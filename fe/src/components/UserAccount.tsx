import { useMember } from "store";
import ProfileUploadButton from "./ProfileUploadButton";

export default function UserAccount() {
  const member = useMember();

  return (
    <>
      <ProfileUploadButton profileUrl={member?.profileImgUrl} />
      <span>{member?.nickname}</span>
    </>
  );
}

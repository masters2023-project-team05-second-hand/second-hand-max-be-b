import { useState } from "react";
import { useMember } from "store";
import styled from "styled-components";
import { NameEditor } from "./NameEditor";
import ProfileUploadButton from "./ProfileUploadButton";

export default function UserAccount() {
  const [member] = useMember();
  const [status, setStatus] = useState<"viewer" | "editor">("viewer");
  const isEditing = status === "editor";

  const changeEditorStatus = () => setStatus("editor");
  const changeViewerStatus = () => setStatus("viewer");

  return (
    <>
      <ProfileUploadButton />
      <NicknameWrapper onDoubleClick={changeEditorStatus}>
        {!isEditing && <NameViewer>{member?.nickname}</NameViewer>}
        {isEditing && <NameEditor onOutsideClick={changeViewerStatus} />}
      </NicknameWrapper>
    </>
  );
}

const NicknameWrapper = styled.div`
  width: 330px;
`;

const NameViewer = styled.div`
  cursor: pointer;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayStrong20};
`;

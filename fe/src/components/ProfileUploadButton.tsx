import { ReactComponent as CameraIcon } from "@assets/icon/camera.svg";
import Button from "@components/common/Buttons/Button";
import { useState } from "react";
import styled from "styled-components";

export default function ProfileUploadButton({
  profileUrl,
}: {
  profileUrl?: string;
}) {
  const [profileImage, setProfileImage] = useState(profileUrl);
  // const [profileFile, setProfileFile] = useState<File | null>(null);

  // TODO: 프로필 수정 요청 바로 보내기
  const onChangeProfileImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      setProfileImage(reader.result as string);
      // setProfileFile(file);
    };
  };

  const onClickProfileImage = () => {
    document.getElementById("profile-upload-input")?.click();
  };

  return (
    <StyledProfile>
      <Button
        size={{ width: 100, height: 100 }}
        backgroundColor="neutralOverlay"
        borderColor="neutralBorder"
        color="accentText"
        radius="half"
        onClick={onClickProfileImage}>
        {profileImage && <ProfileImage src={profileImage} />}
        <CameraIcon className="camera-icon" />
      </Button>
      <input
        type="file"
        id="profile-upload-input"
        onChange={onChangeProfileImage}
      />
    </StyledProfile>
  );
}

const StyledProfile = styled.div`
  display: flex;
  position: relative;
  width: 100px;
  height: 100px;

  #profile-upload-input {
    display: none;
  }
`;

const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  position: absolute;
  object-fit: cover;
  cursor: pointer;
  border-radius: 50%;
  border: 1px solid ${({ theme }) => theme.color.neutralBorder};
  filter: brightness(0.8);
`;

import { patchNickname } from "@api/user";
import useOutsideClick from "@hooks/useOutsideClick";
import { useToast } from "@hooks/useToast";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { useMember } from "store";
import styled from "styled-components";

export const NameEditor = ({
  onOutsideClick,
}: {
  onOutsideClick: () => void;
}) => {
  const [member, setMember] = useMember();
  const [newNickname, setNewNickname] = useState(member?.nickname);

  const { mutate: mutateUserNickname } = useMutation(patchNickname);
  const { toast } = useToast();

  const editNickname = () => {
    const isEditedNickname = newNickname !== member?.nickname;
    const isNicknameEmpty = newNickname === "";

    if (!isEditedNickname || isNicknameEmpty) {
      onOutsideClick();
      return;
    }

    mutateUserNickname(newNickname, {
      onSuccess: () => {
        setMember({
          ...member,
          nickname: newNickname,
        });
        toast({
          type: "success",
          title: "닉네임 변경 성공",
          message: "닉네임이 변경되었습니다.",
        });
      },
      onError: () => {
        toast({
          type: "error",
          title: "닉네임 변경 실패",
          message: "닉네임 변경에 실패했습니다. 잠시 후 다시 시도해주세요.",
        });
      },
      onSettled: () => {
        onOutsideClick();
      },
    });
  };

  const { ref: nameRef } = useOutsideClick<HTMLInputElement>(editNickname);

  const onChangeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewNickname(e.target.value);
  };

  return (
    <InputField>
      <Label htmlFor="nickname-input">아이디</Label>
      <Input
        id="nickname-input"
        ref={nameRef}
        type="text"
        value={newNickname}
        onChange={onChangeName}
        placeholder="내용을 입력하세요"
      />
    </InputField>
  );
};

const InputField = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const Input = styled.input`
  width: 100%;
  border: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  border-radius: ${({ theme: { radius } }) => radius[8]};
  caret-color: ${({ theme: { color } }) => color.accentSecondary};
  box-sizing: border-box;
  padding: 4px 12px 4px 13px;
  font: ${({ theme: { font } }) => font.availableDefault16};

  &::placeholder {
    color: ${({ theme: { color } }) => color.accentTextWeak};
  }
`;

const Label = styled.label`
  width: 100%;
  height: 24px;
  font: ${({ theme: { font } }) => font.displayStrong16};
`;

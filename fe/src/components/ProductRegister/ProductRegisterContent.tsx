import React from "react";
import { styled } from "styled-components";
import { PLACE_HOLDER } from "./constants";
import { AddressInfo } from "@api/type";

export default function ProductRegisterContent({
  content,
  address,
  onChange,
}: {
  content?: string;
  address?: AddressInfo;
  onChange: (content: string) => void;
}) {
  const textAreaRef = React.useRef<HTMLTextAreaElement>(null);

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    onChange(e.target.value);

    if (textAreaRef.current) {
      textAreaRef.current.style.height = "auto";
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
  };

  return (
    <Content
      placeholder={PLACE_HOLDER.CONTENT(
        address?.name ?? PLACE_HOLDER.DEFAULT_PLACE
      )}
      value={content}
      onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
        onContentChange(e)
      }
      ref={textAreaRef}
    />
  );
}

const Content = styled.textarea`
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};
  min-height: 120px;
  border-radius: ${({ theme: { radius } }) => radius[8]};
  padding: 8px;

  &:focus {
    background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};
  }
`;

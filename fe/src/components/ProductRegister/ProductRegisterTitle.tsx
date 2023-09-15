import { styled } from "styled-components";
import { PLACE_HOLDER } from "./constants";

export default function ProductRegisterTitle({
  title,
  onChange,
}: {
  title?: string;
  onChange: (title: string) => void;
}) {
  return (
    <Title
      type="text"
      placeholder={PLACE_HOLDER.title}
      value={title}
      onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
        onChange(e.target.value)
      }
    />
  );
}

const Title = styled.input`
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  font: ${({ theme: { font } }) => font.availableDefault16};
  border-radius: ${({ theme: { radius } }) => radius[8]};
  padding: 8px;

  &:focus {
    background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};
  }
`;

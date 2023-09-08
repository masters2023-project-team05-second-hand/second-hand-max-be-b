import { ReactComponent as XIcon } from "@assets/icon/x.svg";
import Button from "@components/common/Buttons/Button";
import { styled } from "styled-components";
import { ProductImageType } from "./type";

type ImageItemProps = {
  image: ProductImageType;
  onRemoveImage: (id: number) => void;
};

export default function ImageItem({ image, onRemoveImage }: ImageItemProps) {
  return (
    <StyledImageItem>
      <Image src={image.url} />
      <Button
        className="delete"
        size={{ width: 28, height: 28 }}
        leftIcon={<XIcon />}
        color="neutralBackground"
        backgroundColor="neutralTextStrong"
        radius="half"
        onClick={() => onRemoveImage(image.id)}
      />
    </StyledImageItem>
  );
}

const StyledImageItem = styled.li`
  position: relative;

  .delete {
    position: absolute;
    top: -10px;
    right: -10px;
  }
`;

const Image = styled.img`
  width: 80px;
  height: 80px;
  box-sizing: border-box;
  border: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  border-radius: ${({ theme: { radius } }) => radius[16]};
`;

import { ReactComponent as CameraIcon } from "@assets/icon/camera.svg";
import { styled } from "styled-components";
import { ProductImageType } from "./type";
import ImageItem from "./ImageItem";
import { LIMITED_IMAGE_COUNT } from "./constants";
import useDrag from "@hooks/useDrag";

type ProductRegisterImageProps = {
  images: ProductImageType[];
  onAddNewImage: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onRemoveImage: (id: number) => void;
};

export default function ProductRegisterImage({
  images,
  onAddNewImage,
  onRemoveImage,
}: ProductRegisterImageProps) {
  const { ref, onDragStart, onDragEnd, onDragMove } =
    useDrag<HTMLUListElement>();

  return (
    <ProductImage>
      <ProductImageUpload htmlFor="product-upload-input">
        <CameraIcon className="camera-icon" />
        {`${images.length} / ${LIMITED_IMAGE_COUNT}`}
        <input
          type="file"
          id="product-upload-input"
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            onAddNewImage(e)
          }
        />
      </ProductImageUpload>
      <ImageList
        ref={ref}
        onMouseDown={onDragStart}
        onMouseMove={onDragMove}
        onMouseUp={onDragEnd}
        onMouseLeave={onDragEnd}>
        {images.map((image) => {
          return (
            <ImageItem
              key={image.id}
              image={image}
              onRemoveImage={onRemoveImage}
            />
          );
        })}
      </ImageList>
    </ProductImage>
  );
}

const ProductImage = styled.div`
  display: flex;
  flex-direction: row;
  gap: 16px;
  padding-bottom: 16px;
  height: 100px;
  align-items: end;
  border-bottom: ${({ theme: { color } }) =>
    `1px solid ${color.neutralBorder}`};
`;

const ProductImageUpload = styled.label`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4px;
  width: 80px;
  height: 80px;
  box-sizing: border-box;
  border: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
  border-radius: ${({ theme: { radius } }) => radius[16]};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  margin-bottom: 4px;

  &:hover {
    cursor: pointer;
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  .camera-icon {
    filter: ${({ theme: { filter } }) => filter.neutralTextStrong};
  }

  input {
    display: none;
  }
`;

const ImageList = styled.ul`
  flex: 1;
  display: flex;
  align-items: end;
  gap: 16px;
  overflow: scroll;
  height: 100px;

  &::-webkit-scrollbar {
    display: none;
  }
`;

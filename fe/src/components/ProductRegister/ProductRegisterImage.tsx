import { ReactComponent as CameraIcon } from "@assets/icon/camera.svg";
import { styled } from "styled-components";
import { ProductImageType } from "./type";
import ImageItem from "./ImageItem";
import { LIMITED_IMAGE_COUNT } from "./constants";
import { useEffect, useRef } from "react";
import useDrag from "@hooks/useDrag";
import useSideScroll from "@hooks/useSideScroll";
import imageCompression from "browser-image-compression";
import { useToast } from "@hooks/useToast";

type ProductRegisterImageProps = {
  images: ProductImageType[];
  onAddNewImage: ({
    id,
    newImage,
    imageUrl,
  }: {
    id: number;
    newImage: File;
    imageUrl: string;
  }) => void;
  onRemoveImage: (id: number) => void;
};

export default function ProductRegisterImage({
  images,
  onAddNewImage,
  onRemoveImage,
}: ProductRegisterImageProps) {
  const imgListRef = useRef(null);
  const { ref: dragRef, onDragStart, onDragEnd, onDragMove } = useDrag();
  const { scrollRef } = useSideScroll();
  const { toast } = useToast();

  useEffect(() => {
    dragRef.current = imgListRef.current;
    scrollRef.current = imgListRef.current;
  }, [dragRef, scrollRef]);

  const limitImageCount = () => {
    toast({
      title: "이미지 최대 갯수 초과",
      message: "이미지는 최대 10개 업로드 가능합니다",
      type: "error",
    });
  };

  const failImageUpload = () => {
    toast({
      title: "이미지 업로드 실패",
      message: "이미지 업로드에 실패했습니다",
      type: "error",
    });
  };

  const onAddImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;

    if (files === null) return;

    const isImgMaxCount = images.length === LIMITED_IMAGE_COUNT;
    if (isImgMaxCount) {
      limitImageCount();
      return;
    }

    const compressOptions = {
      maxSizeMB: 1,
      maxWidthOrHeight: 1920,
      useWebWorker: true,
    };

    try {
      for (let i = 0; i < files.length; i++) {
        const file = files[i];

        const compressedFile = await imageCompression(file, compressOptions);
        const now = new Date().getTime();

        const reader = new FileReader();
        reader.readAsDataURL(compressedFile);
        reader.onload = () =>
          onAddNewImage({
            id: now + i,
            newImage: compressedFile,
            imageUrl: reader.result as string,
          });
      }
    } catch (error) {
      failImageUpload();
    }

    e.target.value = "";
  };

  return (
    <ProductImage>
      <ProductImageUpload htmlFor="product-upload-input">
        <CameraIcon className="camera-icon" />
        {`${images.length} / ${LIMITED_IMAGE_COUNT}`}
        <input
          type="file"
          multiple={true}
          id="product-upload-input"
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => onAddImage(e)}
        />
      </ProductImageUpload>
      <ImageList
        ref={imgListRef}
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

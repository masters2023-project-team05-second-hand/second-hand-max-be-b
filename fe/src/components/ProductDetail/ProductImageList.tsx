import { ProductImages } from "@api/type";
import styled from "styled-components";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import { Pagination } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";

export default function ProductImageList({
  productImages,
}: {
  productImages: ProductImages[];
}) {
  return (
    <StyledProductImageList>
      <Swiper
        pagination={{
          type: "fraction",
        }}
        className="product-image-swiper"
        modules={[Pagination]}>
        {productImages.map((productImage) => (
          <SwiperSlide key={productImage.id}>
            <ProductImage
              key={productImage.id}
              src={productImage.url}
              alt={`${productImage.id} 상품 이미지`}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </StyledProductImageList>
  );
}

const StyledProductImageList = styled.div`
  width: 100%;
  height: 40vh;
  display: flex;

  .swiper-slide {
    position: relative;

    &::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: linear-gradient(
        69.9deg,
        rgb(76, 79, 106) 3.2%,
        rgb(118, 124, 163) 97.6%
      );
      opacity: 0.3;
    }
  }

  .swiper-pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    bottom: 5%;
    left: 80%;
    gap: 4px;
    width: 60px;
    height: 32px;
    border-radius: ${({ theme: { radius } }) => radius[16]};
    background-color: ${({ theme: { color } }) => color.neutralBackgroundBlur};
    color: ${({ theme: { color } }) => color.neutralTextWeak};
    font: ${({ theme: { font } }) => font.displayDefault12};
  }
`;

const ProductImage = styled.img`
  width: 100%;
  height: 100%;
`;

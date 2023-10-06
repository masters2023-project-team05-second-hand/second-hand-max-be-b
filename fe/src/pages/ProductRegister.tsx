import { useProductDetailQuery } from "@api/product/queries";
import { AddressInfo } from "@api/type";
import ProductRegisterAddress from "@components/ProductRegister/ProductRegisterAddress";
import ProductRegisterCategory from "@components/ProductRegister/ProductRegisterCategory";
import ProductRegisterContent from "@components/ProductRegister/ProductRegisterContent";
import ProductRegisterHeader from "@components/ProductRegister/ProductRegisterHeader";
import ProductRegisterImage from "@components/ProductRegister/ProductRegisterImage";
import ProductRegisterPrice from "@components/ProductRegister/ProductRegisterPrice";
import ProductRegisterTitle from "@components/ProductRegister/ProductRegisterTitle";
import {
  DEFAULT_CATEGORY_ID,
  DEFAULT_SELECTED_ADDRESS_INDEX,
} from "@components/ProductRegister/constants";
import { ProductRegisterInfo } from "@components/ProductRegister/type";
import { Error, Loading } from "@components/common/Guide";
import { Page } from "@styles/common";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAddressListValue, useCurrentAddressIdValue } from "store";
import { styled } from "styled-components";

export default function ProductRegister() {
  const { productId } = useParams();
  const numberProductId = Number(productId);
  const addressList = useAddressListValue();
  const currentAddressId = useCurrentAddressIdValue();
  const [selectedAddressId, setSelectedAddressId] = useState<number | null>(
    currentAddressId
  );
  const [productInfo, setProductInfo] = useState<ProductRegisterInfo>({
    images: [],
    newImages: [],
    deletedImageIds: [],
    title: "",
    categoryId: DEFAULT_CATEGORY_ID,
    price: "",
    content: "",
    address:
      addressList.find((address) => address.id === selectedAddressId) ??
      addressList[DEFAULT_SELECTED_ADDRESS_INDEX],
  });

  const {
    data: productDetailInfo,
    isSuccess,
    isLoading,
    isError,
  } = useProductDetailQuery(numberProductId, !!productId);

  useEffect(() => {
    if (isSuccess && productDetailInfo) {
      setProductInfo((prev) => {
        return {
          ...prev,
          images: productDetailInfo.images,
          title: productDetailInfo.product.title,
          categoryId: productDetailInfo.product.category.id,
          price: productDetailInfo.product.price?.toString() ?? "",
          content: productDetailInfo.product.contents,
          address: productDetailInfo.product.address,
        };
      });

      setSelectedAddressId(productDetailInfo.product.address.id);
    }
  }, [isSuccess, productDetailInfo]);

  const onAddNewImage = ({
    id,
    newImage,
    imageUrl,
  }: {
    id: number;
    newImage: File;
    imageUrl: string;
  }) => {
    setProductInfo((prev) => ({
      ...prev,
      newImages: prev.newImages?.length
        ? [...prev.newImages, { id: id, image: newImage }]
        : [{ id: id, image: newImage }],
      images: [...prev.images, { id: id, url: imageUrl }],
    }));
  };

  const onRemoveImage = (id: number) => {
    const isNewImage = productInfo.newImages?.some((image) => image.id === id);

    if (!isNewImage) {
      setProductInfo((prev) => {
        return {
          ...prev,
          images: prev.images.filter((image) => image.id !== id),
          deletedImageIds: prev.deletedImageIds
            ? [...prev.deletedImageIds, id]
            : [id],
        };
      });
    }

    setProductInfo((prev) => {
      return {
        ...prev,
        images: prev.images.filter((image) => image.id !== id),
        newImages: prev.newImages?.filter((image) => image.id !== id),
      };
    });
  };

  const onChangeText = (
    e:
      | React.ChangeEvent<HTMLInputElement>
      | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setProductInfo((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const onChangeCategory = (categoryId: number) => {
    setProductInfo((prev) => ({
      ...prev,
      categoryId,
    }));
  };

  const onChangeAddress = (
    addressList: AddressInfo[],
    newAddressId: number
  ) => {
    setSelectedAddressId(newAddressId);

    setProductInfo((prev) => ({
      ...prev,
      address:
        addressList.find((address) => address.id === newAddressId) ??
        addressList[DEFAULT_SELECTED_ADDRESS_INDEX],
    }));
  };

  return (
    <Page>
      {!!productId && isLoading ? (
        <Loading
          messages={[
            "상세 상품을 불러오는 중입니다.",
            "새로고침을 하지 마세요!",
          ]}
        />
      ) : isError ? (
        <Error
          messages={[
            "상세 상품을 불러오는데 실패했어요.",
            "잠시 후 다시 시도해주세요.",
          ]}
        />
      ) : (
        <>
          <ProductRegisterHeader productInfo={productInfo} />
          <Main>
            <ProductRegisterImage
              images={productInfo.images}
              onAddNewImage={onAddNewImage}
              onRemoveImage={onRemoveImage}
            />
            <div className="product-info">
              <ProductRegisterTitle
                title={productInfo.title}
                onChange={onChangeText}
              />
              {productInfo.title && (
                <ProductRegisterCategory
                  categoryId={productInfo.categoryId}
                  onChange={onChangeCategory}
                />
              )}
            </div>
            <ProductRegisterPrice
              price={productInfo.price}
              onChange={onChangeText}
            />
            <ProductRegisterContent
              content={productInfo.content}
              address={productInfo.address}
              onChange={onChangeText}
            />
          </Main>
          <ProductRegisterAddress
            selectedAddressId={selectedAddressId}
            address={productInfo.address}
            onChange={onChangeAddress}
          />
        </>
      )}
    </Page>
  );
}

const Main = styled.main`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 24px 16px;
  .product-info {
    display: flex;
    flex-direction: column;
    gap: 8px;
    padding-bottom: 16px;
    border-bottom: ${({ theme: { color } }) =>
      `1px solid ${color.neutralBorder}`};
  }
`;

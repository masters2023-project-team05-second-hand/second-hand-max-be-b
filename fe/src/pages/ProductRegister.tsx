import { patchProduct, postProduct } from "@api/product/index";
import { useProductDetailQuery } from "@api/product/queries";
import { productKeys } from "@api/queryKeys";
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
import { ProductInfo } from "@components/ProductRegister/type";
import { Error, Loading } from "@components/common/Guide";
import { useToast } from "@hooks/useToast";
import { ROUTE_PATH } from "@router/constants";
import { Page } from "@styles/common";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import imageCompression from "browser-image-compression";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAddressListValue, useCurrentAddressIdValue } from "store";
import { styled } from "styled-components";

export default function ProductRegister() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { toast } = useToast();

  const addressList = useAddressListValue();
  const currentAddressId = useCurrentAddressIdValue();

  const { productId } = useParams();
  const numberProductId = Number(productId);

  const [selectedAddressId, setSelectedAddressId] = useState<number | null>(
    currentAddressId
  );
  // Todo: 상태 분리하기 & 상태 관리 라이브러리 쓰기
  const [productInfo, setProductInfo] = useState<ProductInfo>({
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

  // TODO: 아래 두 Mutation 세부 구현 숨기기 위해 hook으로 분리하기
  const newProductMutation = useMutation(postProduct);
  const editProductMutation = useMutation(patchProduct);

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

  const onPostNewProduct = () => {
    const price = productInfo.price.replace(/,/g, "");

    const formData = new FormData();

    productInfo.newImages?.forEach((image) => {
      formData.append("images", image.image);
    });

    formData.append("title", productInfo.title);
    formData.append("content", productInfo.content);
    formData.append("categoryId", JSON.stringify(productInfo.categoryId));
    formData.append("addressId", JSON.stringify(productInfo.address.id));
    formData.append("price", price);

    newProductMutation.mutate(formData, {
      onSuccess: (res) => {
        navigate(ROUTE_PATH.detail + `/${res.data.productId}`, {
          state: { prevRoute: ROUTE_PATH.home },
        });
        queryClient.invalidateQueries(productKeys.detail(numberProductId));
      },
      onError: () => {
        return (
          <Error
            messages={[
              "새로운 상품 등록에 실패했어요.",
              "잠시 후 다시 시도해주세요.",
            ]}
          />
        );
      },
    });
  };

  const onPatchProduct = () => {
    const price = productInfo.price.replace(/,/g, "");

    const formData = new FormData();

    productInfo.newImages?.forEach((image) => {
      formData.append("newImages", image.image);
    });

    if (productInfo.deletedImageIds?.length) {
      formData.append("deletedImageIds", productInfo.deletedImageIds?.join());
    }

    formData.append("title", productInfo.title);
    formData.append("content", productInfo.content);
    formData.append("categoryId", JSON.stringify(productInfo.categoryId));
    formData.append("addressId", JSON.stringify(productInfo.address.id));
    formData.append("price", price);

    editProductMutation.mutate(
      { productId: numberProductId, productInfo: formData },
      {
        onSuccess: () => {
          // Memo: state로 productDetailInfo를 넘겨주기???
          navigate(ROUTE_PATH.detail + `/${productId}`);
          queryClient.invalidateQueries(productKeys.detail(numberProductId));
        },
        onError: () => {
          return (
            <Error
              messages={[
                "상품 수정에 실패했어요.",
                "잠시 후 다시 시도해주세요.",
              ]}
            />
          );
        },
      }
    );
  };

  const onSubmitProduct = () => {
    const isNewProduct = !productId;

    if (isNewProduct) {
      onPostNewProduct();
    } else {
      onPatchProduct();
    }
  };

  const onAddNewImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    // Memo: option은 상의 후 결정
    const options = {
      maxSizeMB: 1,
      maxWidthOrHeight: 1920,
      useWebWorker: true,
    };

    try {
      const compressedFile = await imageCompression(file, options);
      const now = new Date().getTime();

      const reader = new FileReader();
      reader.readAsDataURL(compressedFile);
      reader.onload = () => {
        setProductInfo((prev) => ({
          ...prev,
          newImages: prev.newImages?.length
            ? [...prev.newImages, { id: now, image: compressedFile }]
            : [{ id: now, image: compressedFile }],
          images: [...prev.images, { id: now, url: reader.result as string }],
        }));
      };
    } catch (error) {
      toast({
        title: "이미지 업로드 실패",
        message: "이미지 업로드에 실패했습니다",
        type: "error",
      });
    }
  };

  const onRemoveImage = (id: number) => {
    // Todo: 이미지 없을 때 처리 로직 추가, 상품 삭제시 alert 모달 띄우기
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

  const onChangeTitle = (newTitle: string) => {
    setProductInfo((prev) => ({
      ...prev,
      title: newTitle,
    }));
  };

  const onChangeCategory = (categoryId: number) => {
    setProductInfo((prev) => ({
      ...prev,
      categoryId,
    }));
  };

  const onChangePrice = (price: string) => {
    setProductInfo((prev) => ({
      ...prev,
      price: price,
    }));
  };

  const onChangeContent = (content: string) => {
    setProductInfo((prev) => ({
      ...prev,
      content: content,
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
          <ProductRegisterHeader
            productInfo={productInfo}
            onSubmit={onSubmitProduct}
          />
          <Main>
            <ProductRegisterImage
              images={productInfo.images}
              onAddNewImage={onAddNewImage}
              onRemoveImage={onRemoveImage}
            />
            <div className="product-info">
              <ProductRegisterTitle
                title={productInfo.title}
                onChange={onChangeTitle}
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
              onChange={onChangePrice}
            />
            <ProductRegisterContent
              content={productInfo.content}
              address={productInfo.address}
              onChange={onChangeContent}
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

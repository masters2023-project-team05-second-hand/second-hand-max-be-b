import { Page } from "@styles/common";
import { useState } from "react";
import { styled } from "styled-components";
import AddressButton from "./AddressButton";
import ImageList from "./ImageList";
import NewProductHeader from "./NewProductHeader";

// Memo: 완료 버튼 활성화
// 필수: 사진(1장 이상), 타이틀, 컨텐트, 카테고리????, 동네
// 카테고리 - 랜덤하게 뽑을 때 기타중고물품을 default로 할까??
// 동네 - 전역 상태로 갖고 있는 동네 리스트에서 최근 방문한 동네 id를 찾아서 default로 설정
// 선택: 가격

export default function NewProduct() {
  const [price, setPrice] = useState<string>("");

  const onOpenAddressModal = () => {
    // Todo: 동네 검색 모달 띄워야함
    console.log("동네 모달 열림");
  };

  const getFormattedPrice = (inputValue: string) => {
    const onlyNumber = inputValue.replace(/[^\d]+/g, "");

    if (onlyNumber) {
      const krw = parseInt(onlyNumber).toLocaleString("ko-KR");
      return krw;
    } else {
      return "";
    }
  };

  return (
    <Page>
      <NewProductHeader />
      <Main>
        <ImageList />
        <div className="product-info">
          <div>title</div>
          <div>category</div>
        </div>
        <Price>
          <span className="krw">₩</span>
          <input
            className="price"
            placeholder="가격(선택사항)"
            value={price}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setPrice(getFormattedPrice(e.target.value))
            }
          />
        </Price>
        <Content>content</Content>
      </Main>
      <AddressButton
        addressName="역삼 1동"
        onOpenAddressModal={onOpenAddressModal}
      />
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

const Price = styled.div`
  display: flex;
  gap: 4px;
  padding-bottom: 16px;
  border-bottom: ${({ theme: { color } }) =>
    `1px solid ${color.neutralBorder}`};

  .krw {
    display: block;
    font: ${({ theme: { font } }) => font.displayStrong16};
  }

  .price {
    flex: 1;
    font: ${({ theme: { font } }) => font.availableDefault16};
  }
`;

const Content = styled.div``;

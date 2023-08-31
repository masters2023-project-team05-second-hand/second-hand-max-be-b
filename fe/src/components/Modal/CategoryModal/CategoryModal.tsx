import { categories } from "mocks/data/categories";
import { useState } from "react";
import Modal from "../Modal";
import CategoryModalContent from "./CategoryModalContent";

export default function CategoryModal() {
  // TODO: 더 상위 부모로 상태 올려야 함 (새로운 상품 등록 Context API 적용할지 고민해보기)
  const [selectedCategoryId, setSelectedCategoryId] = useState<number>(1);
  const onClickCategory = (id: number) => {
    setSelectedCategoryId(id);
  };

  return (
    <Modal
      headerProps={{
        title: "카테고리",
        closeHandler: () => console.log("카테고리 모달 닫기"),
      }}
      content={
        <CategoryModalContent
          {...{ categories, selectedCategoryId, onClickCategory }}
        />
      }
      closeHandler={() => console.log("카테고리 모달 닫기")}
    />
  );
}

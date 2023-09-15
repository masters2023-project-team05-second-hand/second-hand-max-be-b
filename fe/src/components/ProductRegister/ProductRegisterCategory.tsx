import { useCategoryQuery } from "@api/product/queries";
import { CategoryInfo } from "@api/type";
import { ReactComponent as ChevronRightIcon } from "@assets/icon/chevron-right.svg";
import CategoryModal from "@components/Modal/CategoryModal/CategoryModal";
import Button from "@components/common/Buttons/Button";
import { TabButtons } from "@components/common/TabButtons";
import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { RANDOM_CATEGORY_COUNT } from "./constants";
import { useToast } from "@hooks/useToast";

export default function ProductRegisterCategory({
  categoryId,
  onChange,
}: {
  categoryId: number;
  onChange: (categoryId: number) => void;
}) {
  const { toast } = useToast();
  const [randomCategories, setRandomCategories] = useState<CategoryInfo[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const { data, isSuccess, isError } = useCategoryQuery();

  useEffect(() => {
    if (isError) {
      toast({
        type: "error",
        title: "카테고리 목록 조회 실패",
        message: "카테고리 목록 조회에 실패했습니다.",
      });
    }
  }, [isError, toast]);

  useEffect(() => {
    if (data && isSuccess) {
      const selectedCategory = data.find(
        (category) => category.id === categoryId
      );
      const randomCategories = getRandomCategories(categoryId, data);

      selectedCategory &&
        setRandomCategories([selectedCategory, ...randomCategories]);
    }
  }, [categoryId, isSuccess, data]);

  const toggleCategoryModal = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Category>
      {randomCategories.length && (
        <TabButtons
          className="tab"
          activeTabId={categoryId}
          tabList={randomCategories}
          onTabClick={onChange}
        />
      )}
      <Button
        size={{ width: 24, height: 24 }}
        leftIcon={<ChevronRightIcon />}
        onClick={toggleCategoryModal}
      />
      {isSuccess && isOpen && (
        <CategoryModal
          selectedId={categoryId}
          onSelectCategory={onChange}
          closeHandler={toggleCategoryModal}
          categories={data}
        />
      )}
    </Category>
  );
}

function getRandomCategories(
  id: number,
  categories: CategoryInfo[]
): CategoryInfo[] {
  const withOutSelectedCategory = categories.filter(
    (category) => category.id !== id
  );

  const shuffledCategories = withOutSelectedCategory.sort(
    () => Math.random() - 0.5
  );
  const slicedCategories = shuffledCategories.slice(0, RANDOM_CATEGORY_COUNT);

  return slicedCategories;
}

const Category = styled.div`
  display: flex;
  justify-content: space-between;
  height: 32px;

  .tab {
    margin: 0;
  }
`;

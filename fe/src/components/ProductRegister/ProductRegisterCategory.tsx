import { CategoryInfo } from "@api/type";
import { ReactComponent as ChevronRightIcon } from "@assets/icon/chevron-right.svg";
import Button from "@components/common/Buttons/Button";
import { useEffect, useState } from "react";
import { styled } from "styled-components";
import CategoryModal from "@components/Modal/CategoryModal/CategoryModal";
import { useCategoryQuery } from "@api/queries";
import { Error, Loading } from "@components/common/Guide";

export default function ProductRegisterCategory({
  category,
  onChange,
}: {
  category: Pick<CategoryInfo, "id" | "name">;
  onChange: (
    id: number,
    categories: Pick<CategoryInfo, "id" | "name">[]
  ) => void;
}) {
  const [randomCategories, setRandomCategories] = useState<CategoryInfo[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const { data, isSuccess, isError, isLoading } = useCategoryQuery();

  useEffect(() => {
    if (data && isSuccess) {
      const randomCategories = getRandomCategories(category.id, data);

      setRandomCategories(randomCategories);
    }
  }, [category.id, isSuccess, data]);

  const toggleCategoryModal = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Category>
      {isLoading ? (
        <Loading
          messages={[
            "카테고리 목록을 불러오는 중입니다.",
            "새로고침을 하지 마세요!",
          ]}
        />
      ) : isError ? (
        <Error
          messages={[
            "카테고리 목록을 불러오는데 실패했어요.",
            "잠시 후 다시 시도해주세요.",
          ]}
        />
      ) : (
        <>
          <CategoryList>
            <SelectedCategory>{category.name}</SelectedCategory>
            <ul>
              {randomCategories.map((category) => (
                <RandomCategory
                  key={category.id}
                  onClick={() => onChange(category.id, randomCategories)}>
                  {category.name}
                </RandomCategory>
              ))}
            </ul>
          </CategoryList>
          <Button
            size={{ width: 24, height: 24 }}
            leftIcon={<ChevronRightIcon />}
            onClick={toggleCategoryModal}
          />
          {isOpen && (
            <CategoryModal
              selectedId={category.id}
              onSelectCategory={onChange}
              closeHandler={toggleCategoryModal}
              categories={data}
            />
          )}
        </>
      )}
    </Category>
  );
}

function getRandomCategories(
  id: number,
  categories: CategoryInfo[]
): CategoryInfo[] {
  const shuffledCategories = categories.sort(() => Math.random() - 0.5);
  const slicedCategories = shuffledCategories.slice(0, 2);

  const isContainSelectedCategory = slicedCategories.some(
    (randomCategory) => randomCategory.id === id
  );

  if (isContainSelectedCategory) {
    return getRandomCategories(id, categories);
  }

  return slicedCategories;
}

const Category = styled.div`
  display: flex;
  justify-content: space-between;
  height: 32px;
`;

const CategoryList = styled.div`
  display: flex;
  gap: 4px;

  ul {
    display: flex;
    gap: 4px;
  }
`;

const SelectedCategory = styled.button`
  padding: 0 16px;
  border-radius: ${({ theme: { radius } }) => radius[50]};
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

const RandomCategory = styled.button`
  padding: 0 16px;
  border-radius: ${({ theme: { radius } }) => radius[50]};
  color: ${({ theme: { color } }) => color.accentTextWeak};
  background-color: ${({ theme: { color } }) => color.accentText};
  font: ${({ theme: { font } }) => font.displayDefault12};
  border: ${({ theme: { color } }) => `1px solid ${color.neutralBorder}`};
`;

import { CategoryInfo } from "@api/type";
import { ROUTE_PATH } from "@router/constants";
import { useNavigate } from "react-router-dom";
import { useCurrentCategoryId } from "store";
import styled from "styled-components";

export default function CategoryList({
  categories,
}: {
  categories: CategoryInfo[];
}) {
  return (
    <StyledCategoryList>
      {categories.map((category) => (
        <CategoryItem key={category.id} category={category} />
      ))}
    </StyledCategoryList>
  );
}

function CategoryItem({ category }: { category: CategoryInfo }) {
  const navigate = useNavigate();
  const moveToHome = () => navigate(ROUTE_PATH.home);

  const [currentCategoryId, setCurrentCategoryId] = useCurrentCategoryId();
  const isActiveCategory = currentCategoryId === category.id;

  const onCategoryItemClick = () => {
    setCurrentCategoryId(category.id);
    moveToHome();
  };

  return (
    <StyledCategoryItem
      $active={isActiveCategory}
      onClick={onCategoryItemClick}>
      <CategoryIcon src={category.imgUrl} alt={category.name} />
      <span>{category.name}</span>
    </StyledCategoryItem>
  );
}

const StyledCategoryList = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
`;

const StyledCategoryItem = styled.div<{ $active: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  font: ${({ $active, theme: { font } }) =>
    $active ? font.availableStrong12 : font.availableDefault12};
`;

const CategoryIcon = styled.img`
  width: 44px;
  height: 44px;
`;

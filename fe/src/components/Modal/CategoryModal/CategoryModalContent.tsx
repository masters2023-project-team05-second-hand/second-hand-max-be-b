import { CategoryInfo } from "@customTypes/index";
import { ListItem, ListPanel } from "../Modal.style";

type CategoryModalContentProps = {
  categories: Pick<CategoryInfo, "id" | "name">[];
  selectedCategoryId: number;
  onClickCategory: (id: number) => void;
};

type CategoryListItemProps = {
  category: Pick<CategoryInfo, "id" | "name">;
  isSelected: boolean;
  onClick: () => void;
};

export default function CategoryModalContent({
  categories,
  selectedCategoryId,
  onClickCategory,
}: CategoryModalContentProps) {
  return (
    <ListPanel>
      {categories.map((category) => (
        <CategoryListItem
          key={category.id}
          category={category}
          isSelected={category.id === selectedCategoryId}
          onClick={() => {
            // TODO: close modal handler 추가
            onClickCategory(category.id);
          }}
        />
      ))}
    </ListPanel>
  );
}

const CategoryListItem = ({
  category,
  isSelected,
  onClick,
}: CategoryListItemProps) => {
  return (
    <ListItem $active={isSelected} onClick={onClick}>
      {category.name}
    </ListItem>
  );
};

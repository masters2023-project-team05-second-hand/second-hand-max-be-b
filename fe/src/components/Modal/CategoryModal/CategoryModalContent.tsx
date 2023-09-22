import { CategoryInfo } from "api/type";
import { ListItem, ListPanel } from "../Modal.style";

type CategoryModalContentProps = {
  categories: CategoryInfo[];
  selectedId: number;
  onClickCategory: (id: number) => void;
};

type CategoryListItemProps = {
  category: CategoryInfo;
  isSelected: boolean;
  onClick: () => void;
};

export default function CategoryModalContent({
  categories,
  selectedId,
  onClickCategory,
}: CategoryModalContentProps) {
  return (
    <ListPanel>
      {categories.map((category) => (
        <CategoryListItem
          key={category.id}
          category={category}
          isSelected={category.id === selectedId}
          onClick={() => {
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

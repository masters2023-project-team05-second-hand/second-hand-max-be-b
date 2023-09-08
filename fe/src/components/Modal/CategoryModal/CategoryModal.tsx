import Modal from "../Modal";
import CategoryModalContent from "./CategoryModalContent";
import useOutsideClick from "@hooks/useOutsideClick";
import { CategoryInfo } from "@api/type";

type CategoryModalProps = {
  selectedId: number;
  onSelectCategory: (
    id: number,
    categories: Pick<CategoryInfo, "id" | "name">[]
  ) => void;
  closeHandler: () => void;
  categories: CategoryInfo[];
};

export default function CategoryModal({
  selectedId,
  onSelectCategory,
  closeHandler,
  categories,
}: CategoryModalProps) {
  const { ref: categoryRef } = useOutsideClick<HTMLDivElement>(closeHandler);

  const onClickCategory = (
    id: number,
    categories: Pick<CategoryInfo, "id" | "name">[]
  ) => {
    onSelectCategory(id, categories);
    closeHandler();
  };

  return (
    <Modal
      ref={categoryRef}
      headerProps={{
        title: "카테고리",
        closeHandler,
      }}
      content={
        <CategoryModalContent
          {...{ categories, selectedId, onClickCategory }}
        />
      }
    />
  );
}

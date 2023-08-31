import CategoryModalContent from "@components/Modal/CategoryModal/CategoryModalContent";
import Modal from "@components/Modal/Modal";
import type { Meta, StoryObj } from "@storybook/react";
import { categories } from "mocks/data/categories";

const meta: Meta<typeof Modal> = {
  component: Modal,
};

export default meta;
type Story = StoryObj<typeof Modal>;

export const CategoryModal: Story = {
  args: {
    headerProps: {
      title: "카테고리",
      closeHandler: () => {},
    },
    content: (
      <CategoryModalContent
        categories={categories}
        selectedCategoryId={1}
        onClickCategory={() => {}}
      />
    ),
  },
};

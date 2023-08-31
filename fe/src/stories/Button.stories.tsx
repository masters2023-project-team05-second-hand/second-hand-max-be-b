import { ReactComponent as ChevronDownIcon } from "@assets/icon/chevron-down.svg";
import { ReactComponent as LayoutGridIcon } from "@assets/icon/layout-grid.svg";
import { ReactComponent as PlusIcon } from "@assets/icon/plus.svg";
import Button from "@components/common/Buttons/Button";
import type { Meta, StoryObj } from "@storybook/react";

const meta: Meta<typeof Button> = {
  component: Button,
};

export default meta;
type Story = StoryObj<typeof Button>;

export const Primary: Story = {
  args: {
    fontName: "availableStrong16",
    rightIcon: <ChevronDownIcon />,
    value: "역삼1동",
  },
};

export const Secondary: Story = {
  args: {
    size: { width: 40, height: 40 },
    leftIcon: <LayoutGridIcon />,
  },
};

export const Large: Story = {
  args: {
    size: { width: 56, height: 56 },
    leftIcon: <PlusIcon />,
    color: "accentText",
    backgroundColor: "accentPrimary",
    radius: "half",
  },
};

export const Small: Story = {
  args: {
    size: { width: 288, height: 56 },
    leftIcon: <PlusIcon />,
    fontName: "availableStrong16",
    color: "accentTextWeak",
    borderColor: "neutralBorder",
    radius: 8,
    value: "추가",
  },
};

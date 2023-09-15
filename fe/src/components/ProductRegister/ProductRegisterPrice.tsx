import { styled } from "styled-components";
import { PLACE_HOLDER } from "./constants";
import { getFormattedPrice } from "@utils/index";

export default function ProductRegisterPrice({
  price,
  onChange,
}: {
  price?: string;
  onChange: (price: string) => void;
}) {
  return (
    <Price>
      <span className="krw">₩</span>
      <input
        className="price"
        placeholder={PLACE_HOLDER.price}
        value={price ? getFormattedPrice(price) : price}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          onChange(getFormattedPrice(e.target.value))
        }
      />
    </Price>
  );
}
const Price = styled.div`
  display: flex;
  gap: 4px;
  padding-bottom: 16px;
  border-bottom: ${({ theme: { color } }) =>
    `1px solid ${color.neutralBorder}`};
  color: ${({ theme: { color } }) => color.neutralTextStrong};

  .krw {
    display: block;
    font: ${({ theme: { font } }) => font.displayStrong16};
  }

  .price {
    flex: 1;
    font: ${({ theme: { font } }) => font.availableDefault16};
    border-radius: ${({ theme: { radius } }) => radius[8]};
    padding: 0 4px;

    &:focus {
      background-color: ${({ theme: { color } }) =>
        color.neutralBackgroundBold};
    }
  }
`;

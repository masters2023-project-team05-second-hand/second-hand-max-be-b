import { TextBold } from "@styles/common";

export default function PriceText({
  productPrice,
}: {
  productPrice: number | null;
}) {
  return (
    <TextBold>
      {productPrice ? `${productPrice.toLocaleString("ko-KR")} 원` : "가격미정"}
    </TextBold>
  );
}

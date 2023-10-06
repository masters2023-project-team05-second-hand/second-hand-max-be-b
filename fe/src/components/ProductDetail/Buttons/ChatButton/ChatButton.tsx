import { ChatItemProduct, SellerInfo } from "@api/type";
import { GoToChatRoomButton } from "./GoToChatRoomButton";
import { GoToProductChatsButton } from "./GoToProductChatsButton";

export type ChatInfo = {
  product: ChatItemProduct;
  seller: SellerInfo;
  chatCount: number;
};

type ChatButtonProps = {
  isSeller: boolean;
  chatInfo: ChatInfo;
};

export default function ChatButton({ isSeller, chatInfo }: ChatButtonProps) {
  const { product, seller, chatCount } = chatInfo;

  return isSeller ? (
    <GoToProductChatsButton {...{ chatCount, productId: product.productId }} />
  ) : (
    <GoToChatRoomButton {...{ product, seller }} />
  );
}

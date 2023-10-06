import {
  useGetProductLikeQuery,
  useMutateProductLike,
} from "@api/user/queries";
import { ReactComponent as HeartFillIcon } from "@assets/icon/heart-filled.svg";
import { ReactComponent as HeartIcon } from "@assets/icon/heart.svg";
import Button from "@components/common/Buttons/Button";
import { debounce } from "lodash";
import { useEffect, useMemo, useState } from "react";
import { useParams } from "react-router-dom";

export default function ProductLikeButton() {
  const { productId } = useParams();
  const numberProductId = Number(productId);

  const { data: userProductLike, isSuccess: isGetProductLikeSuccess } =
    useGetProductLikeQuery(numberProductId);

  const initialIsLiked = useMemo(
    () => userProductLike?.isWished,
    [userProductLike]
  );

  const [isLiked, setIsLiked] = useState<boolean>();
  const toggleLike = () => setIsLiked((prev) => !prev);
  const rollbackLike = () => setIsLiked(initialIsLiked);

  const { mutate: mutateLike } = useMutateProductLike({
    productId: numberProductId,
    onError: rollbackLike,
  });

  // TODO: 새로 받아온 데이터로 업데이트하도록 개선
  useEffect(() => {
    if (isGetProductLikeSuccess) {
      setIsLiked(userProductLike.isWished);
    }
  }, [isGetProductLikeSuccess, userProductLike]);

  useEffect(() => {
    const DEBOUNCE_TIME = 500;
    const isLikedChanged = isLiked !== initialIsLiked;

    const debounceMutateLike = debounce(() => {
      mutateLike({ isWished: isLiked!, productId: productId! });
    }, DEBOUNCE_TIME);

    if (isLikedChanged) {
      debounceMutateLike();
    }

    return () => {
      debounceMutateLike.cancel();
    };
  }, [isLiked, initialIsLiked, mutateLike, productId]);

  return (
    <Button
      size={{ width: 24, height: 24 }}
      color={isLiked ? "accentPrimary" : "neutralTextStrong"}
      leftIcon={isLiked ? <HeartFillIcon /> : <HeartIcon />}
      onClick={toggleLike}
    />
  );
}

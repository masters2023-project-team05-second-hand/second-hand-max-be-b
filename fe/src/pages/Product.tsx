import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import { ReactComponent as DotsIcon } from "@assets/icon/dots.svg";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import useScroll from "@hooks/useScroll";
import { Page } from "@styles/common";
import { useParams } from "react-router-dom";
import { styled } from "styled-components";

export default function Product() {
  const { id } = useParams();
  const { scrollY, ref } = useScroll();

  const isScroll = !!scrollY && scrollY > 0;

  return (
    <Page ref={ref}>
      {/* Todo: 사진 position absolute로 해야함 */}
      <Test>{`상품 ${id} 상세 페이지`}</Test>
      <TopBar
        backgroundColor="accentPrimary"
        isScrolled={isScroll}
        leftBtn={
          <Button
            value="뒤로"
            color="accentText"
            fontName="availableStrong16"
            leftIcon={<ChevronLeftIcon />}
          />
        }
        rightBtn={
          <Button // Todo: 판매자 / 구매자 나눠야 함
            color="accentText"
            leftIcon={<DotsIcon />}
          />
        }
      />
    </Page>
  );
}

const Test = styled.div`
  position: absolute;
  width: 100%;
  height: 2000px;
  background-color: gray;
`;

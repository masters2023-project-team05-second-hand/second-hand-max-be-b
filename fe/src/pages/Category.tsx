import { useCategoryQuery } from "@api/product/queries";
import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import CategoryList from "@components/Category";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { Error, Loading } from "@components/common/Guide";
import useAnimation from "@hooks/useAnimation";
import { slide } from "@styles/animate";
import { Main, StaticPage } from "@styles/common";
import { delay } from "@utils/index";
import { AnimatePresence, motion } from "framer-motion";
import { useNavigate } from "react-router-dom";
import { SLIDE_TIME } from "store/constants";
import styled from "styled-components";

export default function Category() {
  const navigate = useNavigate();
  const { isAnimating, onLeavePage } = useAnimation();
  const { data, isSuccess, isError, isLoading } = useCategoryQuery();

  const moveToPreviousPage = async () => {
    onLeavePage();
    await delay(SLIDE_TIME);
    navigate(-1);
  };

  return (
    <StaticPage>
      <TopBar
        title="카테고리"
        backgroundColor="neutralBackgroundBlur"
        leftBtn={
          <Button
            value="뒤로"
            color="neutralText"
            fontName="availableStrong16"
            onClick={moveToPreviousPage}
            leftIcon={<ChevronLeftIcon />}
          />
        }
        isWithBorder={true}
      />
      <AnimatePresence>
        {isAnimating && (
          <SlideAnimate
            initial="initial"
            animate="in"
            exit="out"
            variants={slide}>
            {isSuccess && (
              <Main>
                <CategoryList categories={data} />
              </Main>
            )}
          </SlideAnimate>
        )}
      </AnimatePresence>
      {isLoading && (
        <Loading
          messages={[
            "카테고리 목록을 불러오는 중입니다.",
            "새로고침을 하지 마세요!",
          ]}
        />
      )}
      {isError && (
        <Error
          messages={[
            "카테고리 목록을 불러오는데 실패했어요.",
            "잠시 후 다시 시도해주세요.",
          ]}
        />
      )}
    </StaticPage>
  );
}

const SlideAnimate = styled(motion.div)``;

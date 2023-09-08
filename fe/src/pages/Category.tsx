import { useCategoryQuery } from "@api/queries";
import { ReactComponent as ChevronLeftIcon } from "@assets/icon/chevron-left.svg";
import CategoryList from "@components/Category";
import TopBar from "@components/TopBar";
import Button from "@components/common/Buttons/Button";
import { Error, Loading } from "@components/common/Guide";
import { Main, Page } from "@styles/common";
import { useNavigate } from "react-router-dom";

export default function Category() {
  const navigate = useNavigate();
  const moveToPreviousPage = () => navigate(-1);

  const { data, isSuccess, isError, isLoading } = useCategoryQuery();

  return (
    <Page>
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
      {isSuccess && (
        <Main>
          <CategoryList categories={data} />
        </Main>
      )}
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
    </Page>
  );
}

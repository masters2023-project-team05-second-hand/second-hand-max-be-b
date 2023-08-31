import { ReactComponent as GithubIcon } from "@assets/icon/github.svg";
import kakao from "@assets/image/kakao.png";
import Button from "./Button";
import { ButtonProps } from "./type";

export function KakaoButton(props: ButtonProps) {
  return (
    <Button {...props}>
      <img src={kakao} alt="카카오 로그인 버튼" />
    </Button>
  );
}

export function GithubButton(props: ButtonProps) {
  return (
    <Button
      {...props}
      leftIcon={<GithubIcon />}
      value="Github 계정으로 로그인"
      size={{ width: 300, height: 45 }}
      backgroundColor="accentTextWeak"
      color="accentText"
      radius={6}
    />
  );
}

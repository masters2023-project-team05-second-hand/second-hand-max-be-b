export const isSameItems = (a: number[], b: number[]) => {
  const setA = new Set(a);
  const setB = new Set(b);

  return setA.size === setB.size && [...setA].every((value) => setB.has(value));
};

export const getFormattedPrice = (inputValue: string) => {
  const onlyNumber = inputValue.replace(/[^\d]+/g, "");
  const limitedPrice = onlyNumber.slice(0, 9);

  if (limitedPrice) {
    const krw = parseInt(limitedPrice).toLocaleString("ko-KR");

    return krw;
  } else {
    return ""; // Notice: inputValue의 length가 0일 경우 NaN 방지
  }
};

export const formatKoreanDate = (date: string) => {
  return new Date(date).toLocaleDateString("ko-KR", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

export function delay(time: number) {
  return new Promise((resolve) => setTimeout(resolve, time));
}

export function getFormattedAddress(addressName: string | undefined) {
  if (!addressName) {
    return "";
  }

  const splittedAddress = addressName.split(" ");
  const dong = splittedAddress[splittedAddress.length - 1];

  return dong;
}

export const checkTokenExpiration = () => {
  const expirationTime = localStorage.getItem("expirationTime");
  const isValidToken =
    !!expirationTime && Date.now() < parseInt(expirationTime, 10);

  return isValidToken;
};

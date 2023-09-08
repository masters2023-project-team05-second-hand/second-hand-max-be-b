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
    return "";
  }
};

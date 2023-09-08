export const getMockAddresses = (page: number) => {
  return mockAddresses[page];
};

const makeAddressPart = (start: number, end: number) => {
  return {
    addresses: Array.from({ length: end - start }, (_, i) => ({
      id: i + start,
      name: `서울 강남구 역삼${i + start}동`,
    })),
    hasNext: true,
  };
};

const mockAddresses = Array.from({ length: 100 }, (_, i) =>
  makeAddressPart(i * 10, (i + 1) * 10)
);

mockAddresses[mockAddresses.length - 1].hasNext = false;

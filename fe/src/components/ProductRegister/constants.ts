export const HUNDRED_MILLION = 9;

export const LIMITED_IMAGE_COUNT = 10;

export const PLACE_HOLDER = {
  TITLE: "내용을 입력하세요",
  PRICE: "가격(선택사항)",
  CONTENT: (address: string) =>
    `${address}에 올릴 게시물 내용을 작성해주세요.(판매금지 물품은 게시가 제한될 수 있어요)`,
  DEFAULT_PLACE: '동네',
  ADDRESS: '동네를 설정해주세요'
};

export const DEFAULT_CATEGORY = {
  id: 1,
  name: "기타 중고물품",
};

export const TEMP_CATEGORY = {
  id: 2,
  name: "인기매물",
  imgUrl: "https://i.ibb.co/LSkHKbL/star.png",
};

export const DEFAULT_SELECTED_ADDRESS_INDEX = 0;
export const DEFAULT_SELECTED_ADDRESS_ID = 1;

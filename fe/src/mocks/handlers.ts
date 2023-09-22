import { PRODUCT_API_PATH } from "@api/product/constants";
import { USER_API_PATH } from "@api/user/constants";
import { AddressInfo, Member, Tokens } from "api/type";
import { rest } from "msw";
import { getMockAddresses } from "./data/address";
import { categories } from "./data/categories";
import { productDetail } from "./data/productDetail";

export const handlers = [
  rest.post(USER_API_PATH.login("kakao"), async (req, res, ctx) => {
    const { accessCode } = await req.json<{ accessCode: string }>();

    if (!accessCode) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json<{ tokens: Tokens }>({
        tokens: {
          accessToken:
            "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjkxOTIyNjAzfQ.vCxUGMiv9bnb4JQGwk6NVx6kHi5hG80tDxafIvrfKbA",
          refreshToken:
            "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTcxMDMwMDN9.FgoFySrenum985OrDzwwtaEhu1Iz7IVJtz5M6H8lzX8",
        },
      })
    );
  }),

  rest.post(USER_API_PATH.login("github"), async (req, res, ctx) => {
    const { accessCode } = await req.json<{ accessCode: string }>();

    if (!accessCode) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json<Tokens>({
        accessToken:
          "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjkxOTIyNjAzfQ.vCxUGMiv9bnb4JQGwk6NVx6kHi5hG80tDxafIvrfKbA",
        refreshToken:
          "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTcxMDMwMDN9.FgoFySrenum985OrDzwwtaEhu1Iz7IVJtz5M6H8lzX8",
      })
    );
  }),

  rest.post(USER_API_PATH.logout, async (req, res, ctx) => {
    const { refreshToken } = await req.json<{ refreshToken: string | null }>();

    if (!refreshToken) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(ctx.status(200));
  }),

  rest.get(PRODUCT_API_PATH.categories, async (_req, res, ctx) => {
    // return res(ctx.status(400), ctx.json({ message: "잘못된 요청입니다." }));
    return res(ctx.status(200), ctx.json(categories));
  }),

  rest.put(USER_API_PATH.memberAddress, async (req, res, ctx) => {
    const { addressIds } = await req.json<{ addressIds: number[] }>();

    if (!addressIds) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json([
        {
          id: 1,
          address: "역삼1동",
        },
        {
          id: 5,
          address: "역삼5동",
        },
      ])
    );
  }),

  rest.post(USER_API_PATH.userProfile, async (req, res, ctx) => {
    const { image } = req.body as { image: File };

    if (!image) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json<{ profileImgUrl: string }>({
        profileImgUrl: "https://avatars.githubusercontent.com/u/111998760?v=4",
      })
    );
  }),

  rest.get(
    `${PRODUCT_API_PATH.products}/:productId`,
    async (_req, res, ctx) => {
      return res(ctx.status(200), ctx.json(productDetail));
    }
  ),

  rest.get(USER_API_PATH.member, async (req, res, ctx) => {
    const Authorization = req.headers.get("Authorization");

    if (!Authorization) {
      return res(
        ctx.status(401),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }
    return res(
      ctx.status(200),
      ctx.json<Member>({
        id: 1,
        nickname: "jjinbbang",
        profileImgUrl:
          "https://github.com/masters2023-project-team05-second-hand/second-hand-max-fe/assets/111998760/4ce425f1-d40b-421f-a24f-3c5b73737120",
      })
    );
  }),

  rest.get(USER_API_PATH.memberAddress, async (_req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json<AddressInfo[]>([
        {
          id: 1,
          name: "역삼 1동",
        },
        {
          id: 5,
          name: "역삼 5동",
        },
      ])
    );
  }),

  rest.get("/api/addresses", (req, res, ctx) => {
    const page = req.url.searchParams.get("page");
    const size = req.url.searchParams.get("size");

    if (!page || !size) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    const currentAddresses = getMockAddresses(parseInt(page));
    return res(ctx.status(200), ctx.json(currentAddresses));
  }),

  rest.patch(USER_API_PATH.nickname, async (req, res, ctx) => {
    const { nickname } = await req.json<{ nickname: string }>();

    if (!nickname) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    if (nickname === "error") {
      return res(
        ctx.status(400),
        ctx.json({
          message: "이미 사용중인 닉네임입니다.",
        })
      );
    }

    return res(ctx.status(200));
  }),

  rest.post(USER_API_PATH.refresh, async (req, res, ctx) => {
    const { refreshToken } = await req.json<{ refreshToken: string }>();

    if (!refreshToken) {
      return res(
        ctx.status(400),
        ctx.json({
          message: "잘못된 요청입니다.",
        })
      );
    }

    return res(
      ctx.status(200),
      ctx.json<{ accessToken: string }>({
        accessToken: "newAccessToken",
      })
    );
  }),

  rest.get(PRODUCT_API_PATH.statuses, async (_req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
        {
          id: 1,
          type: "판매중",
        },
        {
          id: 2,
          type: "예약중",
        },
        {
          id: 3,
          type: "판매완료",
        },
      ])
    );
  }),

  rest.patch(
    `${PRODUCT_API_PATH.products}/:productId/status`,
    async (req, res, ctx) => {
      const { statusId } = await req.json<{ statusId: number }>();

      if (!statusId) {
        return res(
          ctx.status(400),
          ctx.json({
            message: "잘못된 요청입니다.",
          })
        );
      }
      productDetail.product.status = statusId;
      return res(ctx.status(200));
    }
  ),

  rest.get(`${USER_API_PATH.wishlist}/:productId`, async (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        isWished: mockIsWished,
      })
    );
  }),

  rest.post(USER_API_PATH.wishlist, async (req, res, ctx) => {
    const { isWished } = await req.json<{
      productId: string;
      isWished: boolean;
    }>();

    // return res(ctx.status(400));

    mockIsWished = isWished;
    return res(ctx.status(200));
  }),

  rest.delete(
    `${PRODUCT_API_PATH.products}/:productId`,
    async (_, res, ctx) => {
      return res(ctx.status(200));
    }
  ),
];

let mockIsWished = true;

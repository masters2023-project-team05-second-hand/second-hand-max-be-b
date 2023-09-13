package kr.codesquad.secondhand.api.category.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.exception.InvalidCategoryIdException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    POPULAR_ITEMS(1L, "인기매물", "https://i.ibb.co/LSkHKbL/star.png"),
    REAL_ESTATE(2L, "부동산", "https://i.ibb.co/41ScRXr/real-estate.png"),
    USED_CARS(3L, "중고차", "https://i.ibb.co/bLW8sd7/car.png"),
    DIGITAL_DEVICES(4L, "디지털기기", "https://i.ibb.co/cxS7Fhc/digital.png"),
    HOME_APPLIANCES(5L, "생활가전", "https://i.ibb.co/F5z7vV9/domestic.png"),
    FURNITURE_INTERIOR(6L, "가구/인테리어", "https://i.ibb.co/cyYH5V8/furniture.png"),
    BABY_KIDS(7L, "유아동", "https://i.ibb.co/VNKYZTK/baby.png"),
    BABY_BOOKS(8L, "유아도서", "https://i.ibb.co/LrwjRdf/baby-book.png"),
    SPORTS_LEISURE(9L, "스포츠/레저", "https://i.ibb.co/hXVgTyd/sports.png"),
    WOMEN_ACCESSORIES(10L, "여성잡화", "https://i.ibb.co/yPwkyg3/woman-accessories.png"),
    WOMEN_APPAREL(11L, "여성의류", "https://i.ibb.co/4fvj6SC/woman-apparel.png"),
    MEN_APPAREL_AND_ACCESSORIES(12L, "남성패션/잡화", "https://i.ibb.co/wwfyjyB/man-apparel.png"),
    GAMES_AND_HOBBIES(13L, "게임/취미", "https://i.ibb.co/cwJ74M4/game.png"),
    BEAUTY_COSMETICS(14L, "뷰티/미용", "https://i.ibb.co/cXrrK0m/beauty.png"),
    PET_SUPPLIES(15L, "반려동물용품", "https://i.ibb.co/CbwHdNr/pet.png"),
    BOOKS_AND_MUSIC(16L, "도서/음반", "https://i.ibb.co/7WjKkdt/book.png"),
    TICKETS_AND_VOUCHERS(17L, "티켓,교환권", "https://i.ibb.co/kBhhs2p/ticket.png"),
    LIFE(18L, "생활", "https://i.ibb.co/T0mnp8m/kitchen.png"),
    PROCESSED_FOODS(19L, "가공식품", "https://i.ibb.co/S0rSyxr/processed-foods.png"),
    PLANTS(20L, "식물", "https://i.ibb.co/rwZhRqh/plant.png"),
    OTHER_USED_ITEMS(21L, "기타 중고물품", "https://i.ibb.co/tCyMPf5/etc.png"),
    BUYING(22L, "삽니다", "https://i.ibb.co/g7Gc1w0/buy.png");

    private static final List<Category> CATEGORIES;

    private final Long id;
    private final String name;
    private final String imgUrl;

    static {
        CATEGORIES = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<Category> findAll() {
        return CATEGORIES;
    }

    public static Category from(Long categoryId) {
        return Arrays.stream(values())
                .filter(category -> category.id.equals(categoryId))
                .findAny()
                .orElseThrow(InvalidCategoryIdException::new);
    }

    public static List<Category> from(List<Long> categoryId) {
        return categoryId.stream()
                .map(Category::from)
                .collect(Collectors.toUnmodifiableList());
    }
}

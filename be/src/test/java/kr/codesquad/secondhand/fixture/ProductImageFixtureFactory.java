package kr.codesquad.secondhand.fixture;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;

public class ProductImageFixtureFactory {

    public static final URL TEST_URL_1;
    private static final URL TEST_URL_2;

    static {
        try {
            TEST_URL_1 = new URL(
                    "https://s3.ap-northeast-2.amazonaws.com/gamgyul-bucket/public/76696703-fae1-4bcf-98ef-84b64df847f4"
            );
            TEST_URL_2 = new URL(
                    "https://s3.ap-northeast-2.amazonaws.com/gamgyul-bucket/public/eb6ee89e-9347-4914-a0c9-1178e5b6e34a"
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ProductImage> createProductImages(Product product) {
        return ProductImage.from(product, List.of(TEST_URL_1, TEST_URL_2));
    }
}

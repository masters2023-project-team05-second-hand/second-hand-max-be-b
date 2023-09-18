package kr.codesquad.secondhand.util;

import java.net.URL;
import kr.codesquad.secondhand.api.image.util.ImageUploader;
import kr.codesquad.secondhand.fixture.ProductImageFixtureFactory;
import org.springframework.web.multipart.MultipartFile;

public class TestImageUploader implements ImageUploader {

    @Override
    public URL uploadSingleImageToS3(MultipartFile multipartFile) {
        return ProductImageFixtureFactory.TEST_URL_1;
    }
}

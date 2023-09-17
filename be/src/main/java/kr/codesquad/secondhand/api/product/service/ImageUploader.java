package kr.codesquad.secondhand.api.product.service;

import java.net.URL;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    URL uploadSingleImageToS3(MultipartFile multipartFile);
}

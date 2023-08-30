package kr.codesquad.secondhand.api.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // TODO: 예외 처리: 파일 없을 때, 파일 확장자가 이미지가 아닐 때(처리하면 for문 stream으로 수정)
    private List<URL> uploadMultiImagesToS3(List<MultipartFile> multipartFiles) throws IOException {
        List<URL> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            urls.add(uploadSingleImageToS3(multipartFile));
        }
        return urls;
    }

    private URL uploadSingleImageToS3(MultipartFile multipartFile) throws IOException {
        String uuid = UUID.randomUUID().toString(); // 이미지 이름 중복 방지를 위한 고유한 이미지 이름 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        amazonS3.putObject(bucket, uuid, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, uuid);
    }
}

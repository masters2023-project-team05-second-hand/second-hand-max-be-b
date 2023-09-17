package kr.codesquad.secondhand.api.image.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import kr.codesquad.secondhand.api.image.excpetion.ImageUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3ImageUploader implements ImageUploader {

    private final String bucket;
    private final AmazonS3 amazonS3;

    public S3ImageUploader(@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3 amazonS3) {
        this.bucket = bucket;
        this.amazonS3 = amazonS3;
    }

    @Override
    public URL uploadSingleImageToS3(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString(); // 이미지 이름 중복 방지를 위한 고유한 이미지 이름 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3.putObject(bucket, uuid, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ImageUploadFailedException();
        }
        return amazonS3.getUrl(bucket, uuid);
    }
}

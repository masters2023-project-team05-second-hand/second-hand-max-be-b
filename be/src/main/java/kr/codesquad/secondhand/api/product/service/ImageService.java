package kr.codesquad.secondhand.api.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.Image;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void saveAll(List<URL> imageUrls, Product product) {
        List<Image> images = imageUrlsToImage(imageUrls, product);
        imageRepository.saveAll(images);
    }

    public List<URL> uploadMultiImagesToS3(List<MultipartFile> multipartFiles) throws IOException {
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

    public List<Image> imageUrlsToImage(List<URL> imageUrls, Product product) {
        return imageUrls.stream()
                .map(imageUrl -> new Image(product, imageUrl))
                .collect(Collectors.toUnmodifiableList());
    }

    public URL updateImageUrls(Product product, List<MultipartFile> newImages, List<Integer> deletedImageIds)
            throws IOException {

        if (!newImages.isEmpty()) {
            List<Image> newImageEntities = imageUrlsToImage(uploadMultiImagesToS3(newImages), product);
            imageRepository.saveAll(newImageEntities);
        }

        List<Image> images = imageRepository.findAllByProductId(product.getId());
        if (deletedImageIds != null) {
            List<Long> deleteImgIds = deletedImageIds.stream()
                    .map(deleteId -> images.get(deleteId).getId())
                    .collect(Collectors.toUnmodifiableList());
            imageRepository.deleteAllById(deleteImgIds);
        }
        return getThumbnailImgUrl(product.getId());
    }

    private URL getThumbnailImgUrl(Long productId) {
        return imageRepository.findMinByProductId(productId);
    }
}

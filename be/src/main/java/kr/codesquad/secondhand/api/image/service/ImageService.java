package kr.codesquad.secondhand.api.image.service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.image.repository.ImageRepository;
import kr.codesquad.secondhand.api.image.util.ImageUploader;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageUploader imageUploader;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public URL resizeAndUploadToS3(URL imageUrl) {
        // TODO: 리사이징 기능 구현
        return imageUrl;
    }

    public void saveProductImages(List<MultipartFile> multipartFiles, Product product) {
        List<URL> imageUrls = uploadMultiImagesToS3(multipartFiles);
        List<ProductImage> productImages = ProductImage.from(product, imageUrls);
        imageRepository.saveAll(productImages);
    }

    public List<URL> uploadMultiImagesToS3(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(this::uploadSingleImageToS3)
                .collect(Collectors.toUnmodifiableList());
    }

    public URL uploadSingleImageToS3(MultipartFile multipartFile) {
        return imageUploader.uploadSingleImageToS3(multipartFile);
    }

    @Transactional
    public void updateImageUrls(Product product, List<MultipartFile> newImages, List<Long> deletedImageIds) {
        updateNewImagesIfExists(newImages, product);
        deleteTargetImagesIfExists(deletedImageIds);
    }

    private void updateNewImagesIfExists(List<MultipartFile> newImages, Product product) {
        if (newImages != null) {
            saveProductImages(newImages, product);
        }
    }

    private void deleteTargetImagesIfExists(List<Long> deletedImageIds) {
        if (deletedImageIds != null) {
            imageRepository.deleteAllById(deletedImageIds);
        }
    }

    public List<ProductImage> findAllByProductId(Long productId) {
        return imageRepository.findAllByProductId(productId);
    }

    public URL getThumbnailImgUrl(Long productId) {
        return imageRepository.findMinByProductId(productId);
    }

    public void deleteProductImages(Long productId) {
        imageRepository.deleteAllByProductId(productId);
    }
}

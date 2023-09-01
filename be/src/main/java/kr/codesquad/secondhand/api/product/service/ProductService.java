package kr.codesquad.secondhand.api.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.repository.CategoryRepositoryImpl;
import kr.codesquad.secondhand.api.member.domain.Address;
import kr.codesquad.secondhand.api.member.domain.Member;
import kr.codesquad.secondhand.api.member.repository.AddressRepositoryImpl;
import kr.codesquad.secondhand.api.member.service.MemberService;
import kr.codesquad.secondhand.api.product.domain.Image;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.repository.ImageRepository;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import kr.codesquad.secondhand.api.product.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AmazonS3 amazonS3;
    private final MemberService memberService;
    private final StatusRepository statusRepository;
    private final ProductRepository productRepository;
    private final AddressRepositoryImpl addressRepository;
    private final CategoryRepositoryImpl categoryRepository;
    private final ImageRepository imageRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ProductCreateResponse save(ProductCreateRequest productCreateRequest, Long memberId) throws IOException {
        //  TODO 썸네일 리사이징 기능 구현
        // 임시 썸네일 이미지
        List<String> imageUrls = uploadMultiImagesToS3(productCreateRequest.getImages());
        String thumbnailImgUrl = imageUrls.get(0).toString();
        Member seller = memberService.getMemberReferenceById(memberId);
//        id가 아닌 type으로 조회할 경우 쿼리가 발생한다. (쿼리 메소드가 아닌 인터페이스에 추가한 메소드라서?)
//        signInType 처럼 인메모리에 캐싱할 수 있게 리팩토링 필요
        ProductStatus status = statusRepository.getReferenceByType("판매중");
        Address address = addressRepository.getReferenceById(productCreateRequest.getAddressId());
        Category category = categoryRepository.getReferenceById(productCreateRequest.getCategoryId());
        Product product = productCreateRequest.toEntity(seller, status, address, category, thumbnailImgUrl);
        productRepository.save(product);
        List<Image> images = imageUrlsToImage(imageUrls, product);
        imageRepository.saveAll(images);
        return new ProductCreateResponse(product.getId());
    }


    // TODO: 예외 처리: 파일 없을 때, 파일 확장자가 이미지가 아닐 때(처리하면 for문 stream으로 수정)
    private List<String> uploadMultiImagesToS3(List<MultipartFile> multipartFiles) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            urls.add(uploadSingleImageToS3(multipartFile).toString());
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

    private List<Image> imageUrlsToImage(List<String> imageUrls, Product product) {
        return imageUrls.stream()
                .map(imageUrl -> new Image(product, imageUrl))
                .collect(Collectors.toUnmodifiableList());
    }
}

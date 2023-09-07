package kr.codesquad.secondhand.api.product.domain;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private URL url;

    public ProductImage(Product product, URL url) {
        this.product = product;
        this.url = url;
    }

    public static List<ProductImage> from(Product product, List<URL> imageUrls) {
        return imageUrls.stream()
                .map(imageUrl -> new ProductImage(product, imageUrl))
                .collect(Collectors.toUnmodifiableList());
    }
}

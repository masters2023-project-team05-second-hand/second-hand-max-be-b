package kr.codesquad.secondhand.api.product.domain;

import java.net.URL;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    private Date createdTime;

    private Long categoryId;
    private Integer statusId;
    private String title;
    private String content;
    private Long price;
    private URL thumbnailImgUrl;

    @Builder
    public Product(Member seller, Integer statusId, Address address, Category category, String title,
                   String content, Long price, Date createdTime, URL thumbnailImgUrl) {
        this.seller = seller;
        this.statusId = statusId;
        this.address = address;
        this.categoryId = category.getId();
        this.title = title;
        this.content = content;
        this.price = price;
        this.createdTime = createdTime;
        this.thumbnailImgUrl = thumbnailImgUrl;
    }

    public void updateProduct(String title, String content, Long price, Address address, Category category,
                              URL thumbnailImgUrl) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.address = address;
        this.categoryId = category.getId();
        this.thumbnailImgUrl = thumbnailImgUrl;
    }

    public void updateStatus(ProductStatus productStatus) {
        this.statusId = productStatus.getId();
    }

    public boolean isSeller(Member member) {
        return seller.getId().equals(member.getId());
    }
}

package kr.codesquad.secondhand.api.member.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberProfileImgUpdateRequest {

    private MultipartFile newProfileImage;

    public MemberProfileImgUpdateRequest(MultipartFile newProfileImage) {
        this.newProfileImage = newProfileImage;
    }
}

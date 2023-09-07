package kr.codesquad.secondhand.api.product.exception;

public class ImageUploadFailedException extends ProductImageException {

    private static final String ERROR_MESSAGE = "이미지 업로드에 실패했습니다.";

    public ImageUploadFailedException() {
        super(ERROR_MESSAGE);
    }
}

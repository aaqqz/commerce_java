package io.dodn.commerce.core.domain.review;

public final class ReviewPolicyConstants {

    private ReviewPolicyConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }

    /**
     * 리뷰 작성 가능 기간 (일)
     * 결제 완료 후 14일 이내 주문 건에 대해서만 리뷰 작성 가능
     */
    public static final int REVIEW_CREATION_AVAILABLE_DAYS = 14;

    /**
     * 리뷰 수정 가능 기간 (일)
     * 리뷰 작성 후 7일 이내에만 수정 가능
     */
    public static final int REVIEW_UPDATE_AVAILABLE_DAYS = 7;

    /**
     * 리뷰 키 접두사
     */
    public static final String REVIEW_KEY_PREFIX = "ORDER_ITEM_";

    /**
     * 리뷰 키 생성
     *
     * @param orderItemId 주문 항목 ID
     * @return 생성된 리뷰 키
     */
    public static String generateReviewKey(Long orderItemId) {
        return REVIEW_KEY_PREFIX + orderItemId;
    }
}

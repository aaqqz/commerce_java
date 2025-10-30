package io.dodn.commerce.core.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewFinder reviewFinder;

    public RateSummary findRateSummary(ReviewTarget target) {
        return reviewFinder.findRateSummary(target);
    }
}

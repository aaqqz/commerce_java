package io.dodn.commerce.core.domain.review;

import io.dodn.commerce.core.domain.user.User;

public record ReviewKey(
        User user,
        String key
) {
}

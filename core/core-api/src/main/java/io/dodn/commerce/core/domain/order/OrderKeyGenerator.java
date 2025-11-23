package io.dodn.commerce.core.domain.order;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class OrderKeyGenerator {

    // todo kotlin to java
    public String generate() {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                null
//                ByteBuffer.allocate(16)
//                ByteBuffer.allocate(16).apply {
//            UUID.randomUUID().also {
//                putLong(it.mostSignificantBits)
//                putLong(it.leastSignificantBits)
//            }
//        }.array()
        );
    }
}

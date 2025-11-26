package io.dodn.commerce.core.enums;

public enum PaymentState {
    READY,
    SUCCESS,
    ;

    public boolean isReady() {
        return this == READY;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }
}

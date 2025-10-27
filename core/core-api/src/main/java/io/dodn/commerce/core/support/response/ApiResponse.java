package io.dodn.commerce.core.support.response;

import io.dodn.commerce.core.support.error.ErrorMessage;
import io.dodn.commerce.core.support.error.ErrorType;
import lombok.Getter;


@Getter
public final class ApiResponse<T> {

    private final ResultType result;
    private final T data;
    private final ErrorMessage error;

    private ApiResponse(ResultType result, T data, ErrorMessage error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse<Object> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static <S> ApiResponse<S> error(ErrorType error) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
    }

    public static <S> ApiResponse<S> error(ErrorType error, Object errorData) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error, errorData));
    }
}

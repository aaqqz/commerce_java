package io.dodn.commerce.core.support.auth;

import io.dodn.commerce.core.domain.user.User;
import io.dodn.commerce.core.support.error.CoreException;
import io.dodn.commerce.core.support.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new CoreException(ErrorType.INVALID_REQUEST);
        }

        /**
         * NOTE:
         * 해당 서버는 G/W 뒷단에 Internal Network 에 존재하는 것을 가정
         * 유저 인증의 경우 G/W 단에서 처리 한 후 userId 만 보내주는 상황을 가정
         */
        var userId = request.getHeader("DODN-Commerce-User-Id");
        if (userId == null) {
            throw new CoreException(ErrorType.INVALID_REQUEST);
        }

        return new User(Long.parseLong(userId));
    }
}

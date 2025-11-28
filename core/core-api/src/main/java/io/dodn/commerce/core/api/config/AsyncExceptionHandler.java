package io.dodn.commerce.core.api.config;

import io.dodn.commerce.core.support.error.CoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


    @Override
    public void handleUncaughtException(Throwable e, Method method, Object... params) {
        if (e instanceof CoreException) {
            CoreException ce = (CoreException) e;
            LogLevel level = ce.getErrorType().getLogLevel();

            switch (level) {
                case ERROR -> log.error("CoreException : {}", ce.getMessage(), ce);
                case WARN -> log.warn("CoreException : {}", ce.getMessage(), ce);
                default -> log.info("CoreException : {}", ce.getMessage(), ce);
            }
        } else {
            log.error("Exception : {}", e.getMessage(), e);
        }
    }
}

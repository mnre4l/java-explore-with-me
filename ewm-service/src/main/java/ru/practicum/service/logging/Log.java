package ru.practicum.service.logging;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class Log {
    private final HttpServletRequest httpRequest;

    @Pointcut("@annotation(Logging)")
    void loggingProcess() {
    }

    @Before("loggingProcess()")
    @SneakyThrows
    void doLogging() {
        log.info(String.format("%s %s %s", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getQueryString()));
    }
}

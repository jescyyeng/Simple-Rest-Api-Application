package org.example.Aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
@Aspect
@AllArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* org.example.controller..*(..))")
    public Object logHttpRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String requestBody = getRequestBody(joinPoint);
        logger.info("Request: Method: {}, URI: {}, Body: {}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                requestBody
        );

        Object response = null;
        Long endTime;
        Long startTime = System.currentTimeMillis();

        try {
            response = joinPoint.proceed();
            endTime = System.currentTimeMillis();
        } catch(Throwable e) {
            logger.error("Exception: Method: {}, URI: {} failed with exception message: {}",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI(),
                    e.getMessage()
            );
            throw e;
        }
        Long duration = endTime - startTime;
        String responseBody = convertObjectToJson(response);

        logger.info("Response: Method: {}, URI: {}, Status {} - Body: {} Time Taken: {} ms",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpResponse != null ? httpResponse.getStatus() : 0,
                responseBody,
                duration
        );

        return response;
    }

    // Helper method to get request body
    private String getRequestBody(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            try {
                return Arrays.stream(args)
                        .map(this::convertObjectToJson)
                        .reduce((arg1, arg2) -> arg1 + ", " + arg2)
                        .orElse("");
            } catch (Exception e) {
                logger.error("Error serializing request body", e);
            }
        }
        return "";
    }

    // Helper method to convert objects to JSON
    private String convertObjectToJson(Object object) {
        if (object == null)
            return "";
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing object to JSON", e);
            return "Error serializing object to JSON";
        }
    }
}

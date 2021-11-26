package za.co.investec.assessment.clientmanagement.presentation.api.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import za.co.investec.assessment.clientmanagement.presentation.api.model.RequestModel;
import za.co.investec.assessment.clientmanagement.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public final class RequestLoggerAspect {

    @Around("@annotation(requestLogger)")
    public Object logRequest(final ProceedingJoinPoint joinPoint, final RequestLogger requestLogger) throws Throwable {
        try {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            log.info("Request Received: {}", servletRequest.getRequestURI());
            logRequestHeadersAndParameters(servletRequest);
            RequestModel requestBody = getRequestBody(joinPoint);
            if (!Objects.isNull(requestBody)) {
                log.debug("Request Payload: {}", JsonUtils.toString(requestBody));
            }
        } catch (Exception exception) {
            log.warn("An error occurred on requestLogger.", exception);
        }
        Object object = joinPoint.proceed();
        log.info("Request processing finished.");
        return object;
    }

    private void logRequestHeadersAndParameters(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("\n************************************Request Headers***********************************");
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            log.debug("{}: {}", header, request.getHeader(header));
        }
        log.info("\n**************************************************************************************");

        Enumeration<String> parameterNames = request.getParameterNames();
        boolean hasNext = parameterNames.hasMoreElements();
        if (hasNext) {
            log.info("\n************************************Request Parameters***********************************");
            while (hasNext) {
                String parameterName = parameterNames.nextElement();
                log.debug("{}: {}", parameterName, request.getParameter(parameterName));
                hasNext = parameterNames.hasMoreElements();
            }
            log.info("\n**************************************************************************************");
        }
    }

    private RequestModel getRequestBody(ProceedingJoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null && RequestModel.class.isAssignableFrom(arg.getClass())) {
                return (RequestModel) arg;
            }
        }
        return null;
    }
}

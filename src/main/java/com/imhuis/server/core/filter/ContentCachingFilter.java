package com.imhuis.server.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
@Slf4j
//@WebFilter(urlPatterns = "/*", filterName = "contentCacheFilter",
//        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.ASYNC})
public class ContentCachingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("ContentCachingFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("ContentCachingFilter do.");
        String contentType = request.getContentType();
        if (request instanceof HttpServletRequest) {
            if (contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                chain.doFilter(request, response);
            } else {
                ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
                log.info("{}", new String(requestWrapper.getContentAsByteArray()));
                log.info("{}", new String(requestWrapper.getContentAsByteArray()));
                chain.doFilter(requestWrapper, response);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

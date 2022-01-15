package com.imhui.security.filter;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
@Slf4j
@WebFilter(urlPatterns = "/**", filterName = "contentCache")
public class ContentCachingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (request instanceof HttpServletRequest) {
            HttpServletRequest requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            if (contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                chain.doFilter(request, response);
            } else {
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

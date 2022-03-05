package com.imhuis.security.core.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: XSS filter
 */
@Log4j2
@WebFilter(filterName = "xssFilter")
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("[XssFilter] init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("[XssFilter] do.");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 解决动态脚本获取网页cookie，将cookie设置成HttpOnly
        String sessionId = req.getSession().getId();
        resp.setHeader("SET-COOKIE", "JSESSIONID=" + sessionId + "; Secure" + "; HttpOnly");

        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), resp);
    }

    @Override
    public void destroy() {
        log.debug("[XssFilter] destroy.");
    }
}

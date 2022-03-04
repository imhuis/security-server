package com.imhuis.security.core.filter;

import com.imhuis.security.common.constant.SecurityConstants;
import com.imhuis.security.common.exception.CaptchaValidateException;
import com.imhuis.security.common.util.JsonTools;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Component
@Slf4j
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ImageCodeValidateFilter.class);

    private static final String CAPTCHA_VALUE = "captcha";

    private static final String LOGIN_URL = "/login";

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("[ImageCodeValidateFilter] request uri:{}", request.getRequestURI());
        logger.debug("result:{}",StringUtils.equalsIgnoreCase(LOGIN_URL, request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name()));
        if (StringUtils.equalsIgnoreCase(LOGIN_URL, request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name())){
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            try{
                // 之前用 new ServletWebRequest(request) 封装
                validate(requestWrapper);
                request = requestWrapper;
            }catch (AuthenticationException e){
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            } finally {

            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        String codeInRequest = ServletRequestUtils.getStringParameter(request, CAPTCHA_VALUE);
        // multipart/form-data resolve captcha
        if (StringUtils.isEmpty(codeInRequest)) {
            // application/json resolve captcha
            if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
                // InputStream is = requestWrapper.getInputStream() 流只能读取一次
                try (InputStream is = request.getInputStream()) {
                    String requestBody = IOUtils.toString(is, StandardCharsets.UTF_8);
//                    String requestBody = new String(requestWrapper.getRequest().getInputStream().getContentAsByteArray());
                    Map<String, String> authenticationRequestMap = JsonTools.stringToObj(requestBody, Map.class);
                    codeInRequest = authenticationRequestMap.get(CAPTCHA_VALUE);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    throw new CaptchaValidateException("验证码不能为空");
                }
                finally {

                }
            }
        }else {
            throw new CaptchaValidateException("验证码不能为空");
        }
        String s = (String) request.getSession().getAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE);
        if (Objects.isNull(s)){
            throw new CaptchaValidateException("验证码不存在");
        }
        // 判断验证码过期
        if (!StringUtils.equalsIgnoreCase(s, codeInRequest)){
            throw new CaptchaValidateException("验证码错误");
        }
        request.getSession().removeAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE);

    }
}

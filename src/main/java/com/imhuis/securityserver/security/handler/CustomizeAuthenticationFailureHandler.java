package com.imhuis.securityserver.security.handler;

import com.imhuis.securityserver.common.base.ResponseResult;
import com.imhuis.securityserver.common.base.ResponseUtil;
import com.imhuis.securityserver.common.exception.CaptchaValidateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 登录失败处理器
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);

        if (e instanceof LockedException){
            // 账户被锁
            responseResult.setMessage("Account Locked");
        }else if (e instanceof CredentialsExpiredException){
            // 密码过期
            responseResult.setMessage("Credentials Expired");
        }else if (e instanceof AccountExpiredException){
            // 账户过期
            responseResult.setMessage("Account Expired");
        }else if (e instanceof DisabledException){
            // 账户禁用
            responseResult.setMessage("Account Disabled");
        }else if (e instanceof BadCredentialsException){
            // 用户名密码错误
            responseResult.setMessage("Bad Credentials");
        }else if (e instanceof CaptchaValidateException){
            responseResult.setMessage(e.getMessage());
        }
//        response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.success()));
        ResponseUtil.out(response, responseResult);
    }
}

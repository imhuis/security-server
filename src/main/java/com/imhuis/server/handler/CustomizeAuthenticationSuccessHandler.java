package com.imhuis.server.handler;

import com.imhuis.server.common.base.ResponseResult;
import com.imhuis.server.common.base.ResponseUtil;
import com.imhuis.server.security.bo.LoginDetails;
import com.imhuis.server.security.bo.TokenInfo;
import com.imhuis.server.service.AuthService;
import com.imhuis.server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description: 登录成功处理器
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenService tokenService;

    // 查询用户信息
    @Autowired
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();
//        String username = authentication.getName();
        String username = user.getUsername();
        // 删除token
//        tokenService.deleteToken("");
        // 生成token
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(username);
        Set<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream().collect(Collectors.toSet());
//        securityUser.setAuthorities(authorities);

        TokenInfo tokenInfo = tokenService.createToken(loginDetails);

        ResponseResult<TokenInfo> responseResult = new ResponseResult();
        responseResult.setCode(0);
        responseResult.setMessage("Login success");
        responseResult.setData(tokenInfo);
        ResponseUtil.out(response, responseResult);
    }

}

package com.imhui.security.handler;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import com.imhui.security.core.security.bo.SecurityUser;
import com.imhui.security.core.security.bo.TokenInfo;
import com.imhui.security.service.AuthService;
import com.imhui.security.service.TokenService;
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
 * @description:
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
        SecurityUser securityUser = new SecurityUser("");
        Set<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream().collect(Collectors.toSet());
//        securityUser.setAuthorities(authorities);

        TokenInfo tokenInfo = tokenService.createToken(securityUser);

        ResponseResult<TokenInfo> responseResult = new ResponseResult();
        responseResult.setCode(0);
        responseResult.setMessage("Login success");
        responseResult.setData(tokenInfo);
        ResponseUtil.out(response, responseResult);
    }

}

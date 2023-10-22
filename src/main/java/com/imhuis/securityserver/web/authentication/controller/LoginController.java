package com.imhuis.securityserver.web.authentication.controller;

import com.imhuis.securityserver.common.base.ResponseResult;
import com.imhuis.securityserver.common.base.ResponseUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
@RestController
public class LoginController {


    //AuthenticationManager
//    private final AuthenticationManager authenticationManager;
//
//    public LoginController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @RequestMapping("/public/register")
//    public ResponseResult register() {
//        return ResponseUtil.success();
//    }
//
//    @PostMapping("/login")
//    public ResponseResult<Void> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authenticationRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//        Authentication authenticationResponse =
//                this.authenticationManager.authenticate(authenticationRequest);
//        return ResponseUtil.success();
//    }

}

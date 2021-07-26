package com.imhui.security.web.controller;

import com.imhui.security.common.base.ResponseResult;
import com.imhui.security.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OauthController {


    @RequestMapping("/token")
    public ResponseResult login(){
        return ResponseUtil.success();
    }

}

package com.imhuis.securityserver.web.auth.controller;

import com.imhuis.securityserver.common.base.ResponseResult;
import com.imhuis.securityserver.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
@RestController
public class LoginController {

    @RequestMapping("/public/register")
    public ResponseResult register() {
        return ResponseUtil.success();
    }

}

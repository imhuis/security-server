package com.imhuis.securityserver.web.controller;

import com.imhuis.securityserver.common.base.ResponseResult;
import com.imhuis.securityserver.common.base.ResponseUtil;
import com.imhuis.securityserver.security.SecurityUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/me")
    public ResponseResult me(){
        String currentUser = SecurityUtil.getCurrentUser();
        return ResponseUtil.success(currentUser);
    }
}

package com.imhuis.server.web.controller;

import com.imhuis.server.common.base.ResponseResult;
import com.imhuis.server.common.base.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseUtil.success(authentication);
    }
}

package com.imhuis.security.web.controller;

import com.imhuis.security.common.base.ResponseResult;
import com.imhuis.security.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/6
 * @description:
 */
@RestController
public class AppController {

    @RequestMapping("/app/hello")
    public ResponseResult hello(){
        return ResponseUtil.success("Hello, this is app");
    }
}

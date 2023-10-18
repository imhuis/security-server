package com.imhuis.securityserver.web.controller;

import com.imhuis.securityserver.common.base.ResponseResult;
import com.imhuis.securityserver.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@RestController
public class HelloController {


    @RequestMapping("/public/hello")
    public ResponseResult<String> hello(){
        return ResponseUtil.success("Hello World!");
    }

    @RequestMapping("/demo/hello")
    public ResponseResult<String> hi(){
        return ResponseUtil.success("Hi World!");
    }
}

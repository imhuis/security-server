package com.imhuis.server.web.controller;

import com.imhuis.server.common.base.ResponseResult;
import com.imhuis.server.common.base.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: imhuis
 * @date: 2021/9/6
 * @description:
 */
@RestController
public class AppController {

    @RequestMapping("/api/hello")
    public ResponseResult hello(){
        return ResponseUtil.success("Hello, this is app");
    }
}

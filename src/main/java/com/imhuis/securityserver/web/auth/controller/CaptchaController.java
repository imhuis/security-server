package com.imhuis.securityserver.web.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.imhuis.securityserver.common.constant.SecurityConstants;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
@Controller
public class CaptchaController {

    @RequestMapping(value = "/public/captcha.png",method = RequestMethod.GET)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(260, 100, 4, 4);
        request.getSession().setAttribute(SecurityConstants.SESSION_KEY_IMAGE_CODE, captcha.getCode());
//        captcha.write(response.getOutputStream());
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        final ServletOutputStream outputStream = response.getOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
        }
    }

}

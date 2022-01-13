package com.imhui.security.common.util;

import cn.hutool.Hutool;
import cn.hutool.core.lang.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
public class TokenUtil {


    public static String generateToken(){
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        UUID uuid = new UUID(random.nextInt(), random.nextInt());
//        return uuid.toString();
        StringBuilder sb = new StringBuilder("Token ");
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        return sb.append(s).toString();
    }

    public static void main(String[] args) {
        System.out.println(generateToken());
    }
}

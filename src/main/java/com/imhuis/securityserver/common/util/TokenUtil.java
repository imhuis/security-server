package com.imhuis.securityserver.common.util;

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
//        String s = UUID.randomUUID().toString().replaceAll("-", "");
//        return s;
        return "";
    }

    public static void main(String[] args) {
        System.out.println(generateToken());
    }
}

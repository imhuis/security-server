package com.imhui.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.imhui.security.common.util.TokenUtil;
import com.imhui.security.core.security.bo.SecurityUser;
import com.imhui.security.core.security.bo.TokenInfo;
import com.imhui.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2022/1/13
 * @description:
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String TOKEN_IN_REDIS_PREFIX = "security:token:";

    @Override
    public TokenInfo createToken(SecurityUser user) {
        // 缓存保存token信息
        String newToken = TokenUtil.generateToken();
        Duration expires = Duration.ofMinutes(60);
        BoundValueOperations<String, String> vops = redisTemplate.boundValueOps(TOKEN_IN_REDIS_PREFIX + newToken);
        vops.set(user.getUid());
        vops.expire(expires);
//        BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(TOKEN_IN_REDIS_PREFIX + newToken);
//        boundHashOps.putAll(BeanUtil.beanToMap(user));

        StringBuilder sb = new StringBuilder("Token ");
        String formatToken = sb.append(newToken).toString();
        TokenInfo tokenInfo = new TokenInfo(formatToken, expires.getSeconds());
        return tokenInfo;
    }

    @Override
    public void renewalToken(String token) {
        Duration expires = Duration.ofMinutes(60);
        redisTemplate.expire(TOKEN_IN_REDIS_PREFIX + token, expires);
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(TOKEN_IN_REDIS_PREFIX + token);
    }

    @Override
    public String getSecurityUserIdFromToken(String token) {
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.get()
//        BoundValueOperations<String,String> bops = redisTemplate.boundValueOps(TOKEN_IN_REDIS_PREFIX + token);
//        return bops.get();
        return stringRedisTemplate.opsForValue().get(TOKEN_IN_REDIS_PREFIX + token);
    }

    @Override
    public SecurityUser getSecurityUserFromToken(String token) {
        Map entries = redisTemplate.boundHashOps(TOKEN_IN_REDIS_PREFIX + token).entries();
        if (entries.isEmpty()) {
            return null;
        }
        // map 转 object
        return new SecurityUser("");
    }
}

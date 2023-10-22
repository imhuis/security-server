package com.imhuis.securityserver.security.provider;

import com.imhuis.securityserver.domain.securitybo.TokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
//@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("[TokenAuthenticationProvider]");
        String token = (String) authentication.getCredentials();
        // redis鉴权
//        String a = redisTemplate.opsForValue().get(token);
        Boolean tokenIsExist = redisTemplate.hasKey(token);
        // 存在当前token 则刷新redis过期时间
        if (!tokenIsExist) {
            throw new BadCredentialsException("Token is Invalid");
        }

        return new TokenAuthenticationToken(token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public TokenInfo createToken(UserDetails userDetails) {

        return new TokenInfo(userDetails.getUsername(), 3600);
    }
}

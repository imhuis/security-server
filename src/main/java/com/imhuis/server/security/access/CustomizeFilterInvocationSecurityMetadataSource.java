package com.imhuis.server.security.access;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: imhuis
 * @date: 2022/3/8
 * @description:
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PathMatcher pathMatcher;

    private static Map<String, Collection<ConfigAttribute>> configAttributes = new ConcurrentHashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttributes == null) {
//            loadResourceDefine();
        }
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> iterator = configAttributes.keySet().iterator();
        while (iterator.hasNext()) {
            String resURL = iterator.next();
            if (StringUtils.isNotBlank(resURL) && pathMatcher.match(resURL,url)) {
                return configAttributes.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

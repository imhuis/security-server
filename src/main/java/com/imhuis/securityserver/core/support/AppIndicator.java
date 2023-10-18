package com.imhuis.securityserver.core.support;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description:
 */
@Component
public class AppIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }
}

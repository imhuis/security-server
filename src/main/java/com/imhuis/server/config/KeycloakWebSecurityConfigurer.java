package com.imhuis.server.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;

/**
 * @author: imhuis
 * @date: 2022/1/15
 * @description:
 */
//@Configuration
public class KeycloakWebSecurityConfigurer {

    @Bean
    public KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}

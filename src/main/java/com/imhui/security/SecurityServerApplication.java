package com.imhui.security;

import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: imhuis
 * @date: 2021/1/1
 * @description:
 */
@SpringBootApplication(exclude = {KeycloakAutoConfiguration.class})
//@EnableDiscoveryClient
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

}

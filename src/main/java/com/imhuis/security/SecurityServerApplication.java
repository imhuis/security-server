package com.imhuis.security;

import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.consul.ConsulAutoConfiguration;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClientConfiguration;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistryAutoConfiguration;

/**
 * @author: imhuis
 * @date: 2021/1/1
 * @description:
 */
@SpringBootApplication(exclude = {KeycloakAutoConfiguration.class,
        ConsulAutoConfiguration.class,
        ConsulDiscoveryClientConfiguration.class,
        ConsulServiceRegistryAutoConfiguration.class,
        ConsulAutoServiceRegistrationAutoConfiguration.class})
@ServletComponentScan
@EnableDiscoveryClient
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

}

package com.imhuis.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class TimeTest {

    @Test
    public void test(){
        Duration expires = Duration.ofMillis(30);
        System.out.println(expires.getSeconds());
    }

}

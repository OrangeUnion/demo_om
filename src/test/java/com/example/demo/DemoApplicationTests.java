package com.example.demo;

import com.example.demo.clients.BZLMClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class DemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(BZLMClient.getBZLMAccountInfo("VVCUVQ2"));
    }

}

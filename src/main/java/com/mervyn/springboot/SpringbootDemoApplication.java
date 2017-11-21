package com.mervyn.springboot;

import com.mervyn.springboot.demo.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

//@EnableTransactionManagement(order = 2147483647)
@SpringBootApplication
public class SpringbootDemoApplication {

    @Autowired
    private SendMessage sendMessage;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        sendMessage.send();
    }
}

package com.mervyn.springboot.controller;

import com.mervyn.springboot.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mengran.gao on 2017/6/28.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    @Autowired
    private UserProperties userProperties;

    @RequestMapping("/prop")
    public String prop() {
        System.out.println(name + ", " + age);
        System.out.println(userProperties);
        return "Hello Spring Boot!!!";
    }
}

package com.mervyn.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by mengran.gao on 2017/6/28.
 */
@Component
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    private String username;

    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserProperties{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}

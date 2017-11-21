package com.mervyn.springboot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mengran.gao on 2017/11/14.
 */
public class User implements Serializable {

    private Long id;
    private String username;
    private int age;
    private String email;
    private Date birthday;
    private BigDecimal salary;

    public User() {
    }

    public User(String username, int age, String email, Date birthday, BigDecimal salary) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.birthday = birthday;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", salary=" + salary +
                '}';
    }
}

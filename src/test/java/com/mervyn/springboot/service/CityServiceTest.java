package com.mervyn.springboot.service;

import com.mervyn.springboot.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by mengran.gao on 2017/7/12.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Test
    public void add() throws Exception {
        City city = new City();
        city.setName("襄阳市");
        city.setState("3");
        cityService.add(city);
    }

    @Test
    public void findById() throws Exception {
        City city = cityService.findById(1L);
        System.out.println(city);
    }

}
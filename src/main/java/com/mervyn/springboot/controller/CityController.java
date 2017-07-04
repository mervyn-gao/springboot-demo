package com.mervyn.springboot.controller;

import com.mervyn.springboot.model.City;
import com.mervyn.springboot.repository.CityRepository;
import com.mervyn.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mengran.gao on 2017/6/29.
 */
@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/citys", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> list() {
        List<City> all = cityRepository.findAll();
        return all;
    }

    @RequestMapping(value = "/citys", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public City add(City city) throws Exception {
        return cityService.add(city);
    }
}

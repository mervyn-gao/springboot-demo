package com.mervyn.springboot.service.impl;

import com.mervyn.springboot.model.City;
import com.mervyn.springboot.repository.CityRepository;
import com.mervyn.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by mengran.gao on 2017/6/29.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    @Transactional
    public City add(City city) {
        City result = cityRepository.save(city);
//        System.out.println(1 / 0);
        return result;
    }
}

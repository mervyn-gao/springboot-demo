package com.mervyn.springboot.service.impl;

import com.mervyn.springboot.exception.ServiceException;
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
//        City scity = cityRepository.findByName(city.getName());
//        cityRepository.delete(scity);
//        System.out.println(1 / 0);
        if (city.getId() == 1) {
            throw new ServiceException("异常1");
        } else {
            throw new ServiceException("异常2");
        }
//        return cityRepository.save(city);
    }

    @Override
    public City findById(Integer id) {
        return cityRepository.findById(id);
    }
}

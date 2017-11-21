package com.mervyn.springboot.service.impl;

import com.mervyn.springboot.exception.BusinessException;
import com.mervyn.springboot.model.City;
import com.mervyn.springboot.service.CityService;
import com.mervyn.springboot.util.JsonUtils;
import com.mervyn.springboot.vo.BusinessStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by mengran.gao on 2017/6/29.
 */
@Service
public class CityServiceImpl implements CityService {

    private static Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

//    @Autowired
//    private CityRepository cityRepository;

    @Override
//    @Transactional
    public City add(City city) {
//        City scity = cityRepository.findByName(city.getName());
//        cityRepository.delete(scity);
//        System.out.println(1 / 0);
        LOGGER.info("入参：{}", JsonUtils.toJsonString(city));
        if (city.getId() == 1) {
            throw new BusinessException(BusinessStatus.EX_1);
        } else {
            throw new BusinessException(BusinessStatus.EX_2);
        }
//        return cityRepository.save(city);
    }

    @Override
    public City findById(Long id) {
        return null;
//        return cityRepository.findById(id);
    }
}

package com.mervyn.springboot.repository;

import com.mervyn.springboot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mengran.gao on 2017/6/29.
 */
public interface CityRepository extends JpaRepository<City, Long> {
}

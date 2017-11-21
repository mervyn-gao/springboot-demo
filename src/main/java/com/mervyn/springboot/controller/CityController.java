package com.mervyn.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mervyn.springboot.model.City;
import com.mervyn.springboot.service.CityService;
import com.mervyn.springboot.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengran.gao on 2017/6/29.
 */
@RestController
public class CityController {

//    @Autowired
//    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/citys", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<City> list() {
        return null;
//        return cityRepository.findAll();
    }

    @RequestMapping(value = "/citys/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<City> list(@PathVariable Long id) {
        return Result.success(cityService.findById(id));
    }

    @RequestMapping(value = "/citys", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<City> add(City city) {
        return Result.success(cityService.add(city));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> test() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>(3);
        map.put("2", "aa");
        map.put("3", "bb");
        map.put("1", "cc");

        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(map);
        System.out.println(str);
        return map;
    }

    public static void main(String[] args) throws JsonProcessingException {
        TestPOJO pojo = new TestPOJO();
        pojo.setName("yiyi");
        pojo.setAge(10);
        Map<String, Integer> counts = new HashMap<>();
        counts.put("a", 1);
        counts.put("d", 4);
        counts.put("c", 3);
        counts.put("b", 2);
        counts.put("e", 5);
        pojo.setCounts(counts);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
//                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,false);
        String str = mapper.writeValueAsString(pojo);
        System.out.println(str + "xxxxxxxxxx");
    }

    public static class TestPOJO {

        private String name;
        private Integer age;
        private Map<String, Integer> counts;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Integer> getCounts() {
            return counts;
        }

        public void setCounts(Map<String, Integer> counts) {
            this.counts = counts;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}

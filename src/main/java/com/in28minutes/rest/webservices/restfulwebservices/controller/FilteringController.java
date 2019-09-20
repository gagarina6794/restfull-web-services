package com.in28minutes.rest.webservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.rest.webservices.restfulwebservices.model.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filtering")
    public MappingJacksonValue getSomeBean() {
        final SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return getFilters(someBean, "field1", "field2");
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue getListOfSomeBeans() {
        final List<SomeBean> someBeanList = Arrays.asList(new SomeBean("value1", "value2", "value3"),
                new SomeBean("value12", "value22", "value32"));
        return getFilters(someBeanList, "field3");
    }

    private MappingJacksonValue getFilters(Object object, String... fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(fields);
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(object);
        mapping.setFilters(filters);
        return mapping;
    }
}
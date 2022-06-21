package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.bean.UserV2;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class AdminUserController {
    private UserDaoService service;

    @Autowired
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

//    @GetMapping("/v1/admin/users/{id}")
//    @GetMapping(value = "/admin/users/{id}/", params = "version=1")
    @GetMapping(value = "/admin/users/{id}/", headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUserById(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("/v2/admin/users/{id}")
//    @GetMapping(value = "/admin/users/{id}/", params = "version=2")
    @GetMapping(value = "/admin/users/{id}/", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUserByIdV2(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        UserV2 userV2 = UserV2.builder()
                .id(user.getId())
                .name(user.getName())
                .joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .password(user.getPassword())
                .grade("VIP")
                .build();

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }

}

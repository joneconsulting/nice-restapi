package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.AdminUser;
import com.example.myrestfulservice.beans.User;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private UserDaoService service;

    @Autowired
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        for (User user : users) {
            AdminUser adminUser = AdminUser.builder()
                    .id(user.getId()).name(user.getName())
                    .password(user.getPassword()).joinDate(user.getJoinDate())
                    .ssn(user.getSsn())
                    .build();

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/{id}")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        AdminUser adminUser = AdminUser.builder()
                .id(user.getId()).name(user.getName())
                .password(user.getPassword()).joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .build();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "ssn", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

}

package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserDaoService service;

    @Autowired
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET http://localhost:8080/users/10000
    @GetMapping("/{id}")
    public User retrieveUserById(@PathVariable(value = "id") int id) {
        return service.findOne(id);
    }

    @PostMapping("")
    public void createUser(@RequestBody User user) {
        User savedUser = service.save(user);
    }
}

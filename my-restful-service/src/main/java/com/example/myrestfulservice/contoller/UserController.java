package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping("/{id}")
    public User retrieveUserById(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        return user;
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        //  POST http://localhost:8080/users/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable(value = "id") int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> modifyUserById(@PathVariable(value = "id") int id,
                               @RequestBody User user) {
        user.setId(id);
        User modifiedUser = service.updateUserById(user);

        if (modifiedUser == null) {
            throw new UserNotFoundException("id-" + id);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).build();
    }
}

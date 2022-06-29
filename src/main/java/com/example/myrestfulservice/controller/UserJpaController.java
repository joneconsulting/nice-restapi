package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import com.example.myrestfulservice.service.UserJpaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa/users")
public class UserJpaController {
    private UserJpaService service;

    @Autowired
    public UserJpaController(UserJpaService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "전체 사용자 목록 조회(DB)", notes = "DB에 저장된 전체 사용자의 목록을 조회합니다.")
    public List<User> retrieveAllUsers() {
        List<User> users = service.getAllUsers();

        return users;
    }

    // http://localhost:8088/jpa/users/90001 -> String -> Integer (casting)
    @GetMapping(value = "/{id}", headers = "searchType=id")
    public ResponseEntity retrieveUserById(@PathVariable(value = "id") int id) {
        User user = service.getUserById(id);

        if (user == null) {
            throw new UserNotFoundException("ID-" + id);
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // http://localhost:8088/users
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    // http://localhost:8088/jpa/users/User1 -> String
    @GetMapping(value = "/{name}", headers = "searchType=name")
    public ResponseEntity retrieveUserByName(@PathVariable(value = "name") String name) {
        User user = service.getUserByName(name);

        if (user == null) {
            throw new UserNotFoundException("Name-" + name);
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // http://localhost:8088/users
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable(value = "id") int id) {
        service.deleteUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.createUser(user);

        // http://localhost:8088/jpa/users
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateUserById(@PathVariable(value = "id") int id,
                                         @RequestBody User user) { // name, password
        User updatedUser = service.updateUserById(id, user);

        if (updatedUser == null) {
            throw new UserNotFoundException("id-" + id);
        }

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
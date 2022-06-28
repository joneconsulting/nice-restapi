package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserDaoService service;

    @Autowired
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "전체 사용자 목록 조회", notes = "등록된 전체 사용자의 목록을 조회합니다.")
    public ResponseEntity<CollectionModel<EntityModel<User>>> retrieveAllUsers() {
        List<User> users = service.findAll();
        List<EntityModel<User>> result = new ArrayList<>();

        for (User user : users) {
            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveUser(user.getId())).withRel("detail"));

            result.add(entityModel);
        }

        return ResponseEntity.ok(CollectionModel.of(result,
                linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "사용자 상세정보 조회", notes = "사용자 아이디를 이용하여 사용자의 정보를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 아이디", required = true, paramType = "path")
    })
    public ResponseEntity retrieveUser(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // http://localhost:8088/users
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    // http://localhost:8088/users/4
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") int id) {
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

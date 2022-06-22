package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<User>>> retrieveAllUsers() {
        List<EntityModel<User>> result = new ArrayList<>();
        List<User> users = service.findAll();

        for (User user : users) {
            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveUserById(user.getId())).withRel("view"));

            result.add(entityModel);
        }

        return ResponseEntity.ok(CollectionModel.of(result, linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity retrieveUserById(@PathVariable(value = "id") int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
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

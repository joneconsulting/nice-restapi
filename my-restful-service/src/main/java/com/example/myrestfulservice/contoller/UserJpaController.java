package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.jpa.UserRepository;
import com.example.myrestfulservice.vo.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private UserRepository userRepository;

    @Autowired
    public UserJpaController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users")
    public ResponseEntity retrieveAllUsers() {
        List<User> users = userRepository.findAll();

        ResponseData response = ResponseData.builder()
                .count(users == null || users.isEmpty() ? 0 : users.size())
                .users(users)
                .build();

        EntityModel entityModel = EntityModel.of(response);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity retrieveUserById(@PathVariable(value = "id") int id) {
        // JPA -> 데이터 조회 함수 호출
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        // HATEOAS 추가 (전체 사용자 목록 보기)
        EntityModel entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUserById(@PathVariable(value = "id") int id) {
        userRepository.deleteById(id);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity updateUserById(@PathVariable(value = "id") int id,
                                         @RequestBody User user) {
        // JPA -> 데이터 조회 함수 호출
        Optional<User> storedUser = userRepository.findById(id);

        if (!storedUser.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        user.setId(id);
        userRepository.save(user); // update sql

        return ResponseEntity.noContent().build(); // 204
    }
}

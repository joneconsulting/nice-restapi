package com.example.myrestfulservice.service;

import com.example.myrestfulservice.beans.User;
import com.example.myrestfulservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserJpaService {
    private UserRepository userRepository;

    @Autowired
    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        users.stream().forEach(v -> {
            log.info("User -> " + v.getId() + "/" + v.getName());
        });

        return users;
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);

        return user == null ? null : user.get();
    }

    public User getUserByName(String name) {
        User user = userRepository.findByName(name);

        return user;
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public User createUser(User user) {
        user.setJoinDate(new Date());
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    // update user set join_date=null, name='input name', password='input pwd', ssn='db ssn' where id=90001
    public User updateUserById(int id, User user) { // {name, password}
        Optional<User> _optional = userRepository.findById(id);

        if (!_optional.isPresent()) {
            return null;
        }

        User storedUser = _optional.get();

        user.setId(id);
        user.setSsn(storedUser.getSsn());
        user.setJoinDate(storedUser.getJoinDate()); //user.setJoinDate(new Date());
        userRepository.save(user); // update sql

        return user;
    }
}

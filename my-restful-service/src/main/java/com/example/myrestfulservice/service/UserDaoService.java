package com.example.myrestfulservice.service;

import com.example.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(User.builder().id(1).name("Kenneth").joinDate(new Date()).build());
        users.add(User.builder().id(2).name("Alice").joinDate(new Date()).build());
        users.add(User.builder().id(3).name("Elena").joinDate(new Date()).build());
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

    public User updateUserById(User newUser) {
        User storedUser = findOne(newUser.getId());
        if (storedUser == null) {
            return null;
        } else {
            storedUser.setName(newUser.getName());
            storedUser.setJoinDate(new Date());
            return storedUser;
        }
    }
}

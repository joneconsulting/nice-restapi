package com.example.myrestfulservice.repository;

import com.example.myrestfulservice.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name); // select * from user where name=?
}

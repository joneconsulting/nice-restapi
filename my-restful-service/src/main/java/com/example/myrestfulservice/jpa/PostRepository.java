package com.example.myrestfulservice.jpa;

import com.example.myrestfulservice.bean.Post;
import com.example.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}

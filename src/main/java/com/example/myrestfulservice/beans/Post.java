package com.example.myrestfulservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    private User user;
}

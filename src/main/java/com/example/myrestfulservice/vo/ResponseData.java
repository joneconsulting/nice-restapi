package com.example.myrestfulservice.vo;

import com.example.myrestfulservice.beans.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private Integer count;
    private List<User> users;
}

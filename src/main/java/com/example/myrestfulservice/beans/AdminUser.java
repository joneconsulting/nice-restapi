package com.example.myrestfulservice.beans;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonFilter("UserInfo")
@Builder
public class AdminUser {
    private Integer id;
    @Size(min = 2, message = "이름은 2글자 이상 입력해 주세요.")
    private String name;

    @Past
    private Date joinDate;

    private String password;
    private String ssn;
}

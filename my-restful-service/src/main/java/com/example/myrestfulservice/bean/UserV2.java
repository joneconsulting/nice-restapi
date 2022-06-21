package com.example.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonFilter("UserInfoV2")
public class UserV2 {
    private Integer id;
    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;

    private String password;
    private String ssn;

    private String grade;
}

package com.thoughtworks.rslist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String userName;
    private Integer age;
    private String gender;
    private String email;
    private String phoneNumber;

    public UserDto(String userName, Integer age, String gender, String email, String phoneNumber) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

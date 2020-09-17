package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(max = 8)
    private String userName;

    @NotNull
    @Max(100)
    @Min(18)
    private Integer age;

    @NotEmpty
    private String gender;

    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^1\\d{10}$")
    private String phoneNumber;

    @Override
    public String toString() {
//        {
//            "user_name": "xxxx",
//                "user_age": 19,
//                "user_gender": "female",
//                "user_email": "xxx@xx",
//                "user_phone": "1xxxxxxxxxx"
//        }
        return "{" +
                "\"user_name\": \"" + userName +
                "\",\"user_age\":" + age +
                ", \"user_gender\": \"" + gender +
                "\", \"user_email\":" + email +
                ", \"user_phone\": \"" + phoneNumber +
                "\"}";
    }
}
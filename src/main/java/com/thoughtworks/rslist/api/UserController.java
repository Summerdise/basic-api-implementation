package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Entity.UserEntity;
import com.thoughtworks.rslist.Repository.UserRepository;
import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private List<UserDto> userDtoList = new ArrayList<>();

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/register")
    public void register(@Valid @RequestBody UserDto userDto){
        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .phone(userDto.getPhoneNumber())
                .build();
        userRepository.save(userEntity);

    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUserList(){
        return ResponseEntity.ok(userDtoList.toString());
    }

}

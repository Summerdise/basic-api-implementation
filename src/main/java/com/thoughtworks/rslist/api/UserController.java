package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Entity.UserEntity;
import com.thoughtworks.rslist.Repository.RsEventRepository;
import com.thoughtworks.rslist.Repository.UserRepository;
import com.thoughtworks.rslist.dto.UserDto;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private List<UserDto> userDtoList = new ArrayList<>();

    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;



    @PostMapping("/user/add")
    public ResponseEntity add(@Valid @RequestBody UserDto userDto){
        userDtoList.add(userDto);
        return ResponseEntity.created(null).build();
    }

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

    @GetMapping("/user/delete")
    public ResponseEntity<String> deleteAllUser(){
        userRepository.deleteAll();
        return ResponseEntity.ok(userDtoList.toString());
    }

    @GetMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.ok(userDtoList.toString());
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/delete/{index}")
    public void deleteUsers(@PathVariable int index){
        userRepository.deleteById(index);
    }
}

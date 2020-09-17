package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsItem {

    @NotEmpty
    private String name;
    @NotEmpty
    private String keyword;

    @NotNull
    private @Valid UserDto userDto;

    @JsonIgnore
    public UserDto getUser() {
        return userDto;
    }

    @JsonProperty
    public void setUser(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return name + ',' + keyword +','+getUser().getUserName();
    }

}

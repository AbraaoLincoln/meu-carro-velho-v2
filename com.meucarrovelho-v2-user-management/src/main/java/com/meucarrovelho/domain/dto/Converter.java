package com.meucarrovelho.domain.dto;

import com.meucarrovelho.domain.entity.User;

public class Converter {

    public static User newUserDtoToUser(NewUserDto newUserDto) {
        var user = new User();

        user.setName(newUserDto.getName());
        user.setPassword(newUserDto.getPassword());
        user.setEmail(newUserDto.getEmail());

        return user;
    }
}

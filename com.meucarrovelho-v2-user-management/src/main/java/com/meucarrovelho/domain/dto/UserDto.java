package com.meucarrovelho.domain.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String name;
    private String password;
    private String email;
}

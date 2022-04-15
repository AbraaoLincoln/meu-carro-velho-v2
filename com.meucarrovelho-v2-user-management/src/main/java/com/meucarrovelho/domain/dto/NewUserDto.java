package com.meucarrovelho.domain.dto;

import lombok.Data;

@Data
public class NewUserDto {
    private String name;
    private String password;
    private String email;
}

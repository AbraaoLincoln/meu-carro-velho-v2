package com.meucarrovelho.utils;

import com.meucarrovelho.domain.entity.User;

public class TestUtils {
    public static User createUser() {
        var newUser = new User();
        newUser.setName("teste");
        newUser.setPassword("123");
        newUser.setEmail("teste@mail.com");
        return newUser;
    }

    public static User generateIdForUser(User user) {
        user.setId(1);
        return user;
    }
}

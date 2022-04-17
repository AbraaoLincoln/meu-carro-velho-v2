package com.meucarrovelho.domain.repository;

import com.meucarrovelho.domain.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public boolean existsByEmail(String email) {
        var queryResult = list("select email from User where email = ?1", email);

        return queryResult.size() > 0;
    }
}

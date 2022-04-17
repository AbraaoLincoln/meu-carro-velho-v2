package com.meucarrovelho.domain.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static com.meucarrovelho.utils.TestUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @Transactional
    public void shoudFindEmail() {
        //Given
        var user = createUser();
        userRepository.persist(user);

        //When
        var result = userRepository.existsByEmail(user.getEmail());

        //Then
        assertTrue(result);

        //cleaing up database
        userRepository.delete(user);
    }

    @Test
    @Transactional
    public void shoudNotFindEmail() {
        //Given
        var user = createUser();
        userRepository.persist(user);

        //When
        var result = userRepository.existsByEmail("user1@mail.com");

        //Then
        assertFalse(result);

        //cleaing up database
        userRepository.delete(user);
    }
}

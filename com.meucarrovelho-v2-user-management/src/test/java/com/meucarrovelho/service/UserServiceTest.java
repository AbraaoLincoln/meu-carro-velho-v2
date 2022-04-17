package com.meucarrovelho.service;

import com.meucarrovelho.domain.entity.User;
import com.meucarrovelho.domain.repository.UserRepository;
import com.meucarrovelho.exception.BusinessException;
import com.meucarrovelho.exception.ExceptionMessage;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static com.meucarrovelho.utils.TestUtils.createUser;
import static com.meucarrovelho.utils.TestUtils.generateIdForUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class UserServiceTest {

    @InjectMock
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Test
    public void shouldSaveNewUSer() {
        //Given
        var user = createUser();
        Mockito.doAnswer((answer) -> {
            return generateIdForUser(user);
        }).when(userRepository).persist(user);

        //When
        var savedUser = userService.createNewUser(user);

        //Then
        assertEquals(1, savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).persist(user);
    }

    @Test
    public void shouldNotSaveNewUSerWithInvalidEmail() {
        //Given
        var user = createUser();
        user.setEmail("teste@mail");
        Mockito.doAnswer((answer) -> {
            return generateIdForUser(user);
        }).when(userRepository).persist(user);

        //When
        try{
            var savedUser = userService.createNewUser(user);
            fail("saved user with invalid email");
        //Then
        }catch (BusinessException be) {
            assertTrue(be instanceof BusinessException);
            assertEquals(ExceptionMessage.INVALID_EMAIL, be.getMessage());
            verify(userRepository, times(0)).persist(user);
        }
    }

    @Test
    public void shouldNotSaveNemUserWithEmailAlreadyRegister() {
        //Given
        var user = createUser();
        Mockito.when(userRepository.existsByEmail(Mockito.anyString()))
                .thenReturn(true);
        Mockito.doAnswer((answer) -> {
            return generateIdForUser(user);
        }).when(userRepository).persist(user);

        //When
        try{
            userService.createNewUser(user);
            fail("saved user with email already in use");

        //Then
        }catch (BusinessException be) {
            assertTrue(be instanceof BusinessException);
            assertTrue(user.getId() == 0);
            assertEquals(be.getMessage(), ExceptionMessage.EMAIL_ALREADY_IN_USE);
            verify(userRepository, times(0)).persist(user);
        }

    }
}

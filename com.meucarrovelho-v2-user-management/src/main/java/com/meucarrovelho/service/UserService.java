package com.meucarrovelho.service;

import com.meucarrovelho.domain.entity.User;
import com.meucarrovelho.domain.repository.UserRepository;
import com.meucarrovelho.exception.BusinessException;
import com.meucarrovelho.exception.ExceptionMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserService {
    @Inject
    private UserRepository userRepository;

    @Transactional
    public User createNewUser(User userToSave) {
        validateEmail(userToSave.getEmail());
        //TODO: hash password
        userRepository.persist(userToSave);
        return userToSave;
    }

    private void  validateEmail(String email) {
        Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher result = emailRegex.matcher(email);

        if(!result.find())
            throw new BusinessException(ExceptionMessage.INVALID_EMAIL);
    }
}

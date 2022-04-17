package com.meucarrovelho.resource;

import com.meucarrovelho.domain.entity.User;
import com.meucarrovelho.exception.BusinessException;
import com.meucarrovelho.exception.ExceptionMessage;
import com.meucarrovelho.service.UserService;
import com.meucarrovelho.utils.TestUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.meucarrovelho.utils.TestUtils.createUser;
import static com.meucarrovelho.utils.TestUtils.generateIdForUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserResourceTest {
    private final String USERS_API = "/api/users";

    @InjectMock
    UserService userService;

    @Test
    public void shouldSaveNewUser() {
        //setUp
        var newUser = createUser();
        var savedUser = createUser();
        Mockito.when(userService.createNewUser(Mockito.any(User.class)))
                .thenReturn(generateIdForUser(savedUser));

        //
        given()
        .contentType(ContentType.JSON)
        .body(newUser)
        .when()
        .post(USERS_API)
        .then()
        .statusCode(RestResponse.StatusCode.CREATED)
        .contentType(ContentType.JSON)
        .body("id", equalTo(savedUser.getId()))
        .body("name", equalTo(savedUser.getName()))
        .body("email", equalTo(savedUser.getEmail()));
    }

    @Test
    public void shouldNotSaveNewUserWithInvalidEmail() {
        //setUp
        var newUser = createUser();
        Mockito.when(userService.createNewUser(Mockito.any(User.class)))
                    .thenThrow(new BusinessException(ExceptionMessage.INVALID_EMAIL));

        //
        given()
        .contentType(ContentType.JSON)
        .body(newUser)
        .when()
        .post(USERS_API)
        .then()
        .statusCode(RestResponse.StatusCode.BAD_REQUEST)
        .body(is(ExceptionMessage.INVALID_EMAIL));
    }

    @Test
    public void shouldNotSaveNemUserWithEmailAlreadyRegister() {
        //Setup
        var user = createUser();
        Mockito.when(userService.createNewUser(Mockito.any(User.class)))
                .thenThrow( new BusinessException(ExceptionMessage.EMAIL_ALREADY_IN_USE));

        given()
        .contentType(ContentType.JSON)
        .body(user)
        .when()
        .post(USERS_API)
        .then()
        .statusCode(RestResponse.StatusCode.BAD_REQUEST)
        .body(is(ExceptionMessage.EMAIL_ALREADY_IN_USE));
    }
}

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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserResourceTest {

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
        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(newUser)
                   .when()
                   .post("/api/users")
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
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/api/users")
                .then()
                .statusCode(RestResponse.StatusCode.BAD_REQUEST)
                .body(is(ExceptionMessage.INVALID_EMAIL));
    }
}

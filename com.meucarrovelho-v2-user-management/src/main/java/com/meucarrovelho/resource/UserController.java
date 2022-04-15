package com.meucarrovelho.resource;

import com.meucarrovelho.domain.dto.Converter;
import com.meucarrovelho.domain.dto.NewUserDto;
import com.meucarrovelho.exception.BusinessException;
import com.meucarrovelho.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/api/users")
public class UserController {
    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(NewUserDto newUserDto) throws URISyntaxException {
        try {
            System.out.println(newUserDto);
            var user = userService.createNewUser(Converter.newUserDtoToUser(newUserDto));
            System.out.println(user);
            return Response.created(new URI("/users")).entity(user).build();
        }catch (BusinessException be) {
            return Response.status(Response.Status.BAD_REQUEST).entity(be.getMessage()).build();
        }
    }
}

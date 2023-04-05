package academy.mindera.quarkus_aws.microservices.user;

import academy.mindera.quarkus_aws.microservices.common.dynamodb.EntityType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/api/users")
public class UserResource {

    @Inject
    UserRepository userRepository;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return Response.ok()
                .entity(
                        userRepository.findAll()
                )
                .build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(User.UserCreateDto userCreateDto) {
        try {
            User user =   userRepository.save(userCreateDto.toUser());
            return Response.ok()
                    .entity(user)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User already exists")
                    .build();
        }

    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response me() {
        String pk = EntityType.USER.prefixPK() +  jwt.getClaim("sub");
        User user = Optional.of(userRepository.findByPk(pk))
                .orElseThrow(
                        () -> new WebApplicationException("User not found")
                );
        return Response.ok()
                .entity(user)
                .build();
    }
}

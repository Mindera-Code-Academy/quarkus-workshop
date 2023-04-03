package academy.mindera.quarkus_aws.microservices.common;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class CommonResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(
            @QueryParam("idToken") String idToken
    ) {
        return idToken;
    }
}
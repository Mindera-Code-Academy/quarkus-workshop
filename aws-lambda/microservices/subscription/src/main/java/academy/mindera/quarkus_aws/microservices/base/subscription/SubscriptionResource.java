package academy.mindera.quarkus_aws.microservices.base.subscription;



import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Path("/api/subscriptions")
public class SubscriptionResource {

    @Inject
    SubscriptionRepository subscriptionRepository;

    @Inject
    SecurityContext securityContext;

    @Inject
    JsonWebToken jwt;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubscription(Subscription.SubscriptionDto subscriptionDto) throws URISyntaxException, UnsupportedEncodingException {
        Subscription subscription;

        if(!subscriptionDto.userPK().contains(jwt.getClaim("sub"))) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("You can only create subscriptions for yourself")
                    .build();
        }

        try {
            subscription = subscriptionRepository.save(subscriptionDto.toSubscription());
            return Response.created(
                            new URI("/api/subscriptions/user/" + URLEncoder.encode(subscription.getGSI2PK(), StandardCharsets.UTF_8))
                    )
                    .entity(subscription)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Subscription already exists")
                    .contentLocation(new URI("/api/subscriptions/user/" + URLEncoder.encode(subscriptionDto.userPK(), StandardCharsets.UTF_8)))
                    .build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptions() {
     return Response.ok()
        .entity(subscriptionRepository.findAll())
        .build();
    }

    @GET
    @Path("/user/{userPK}")
   // @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptionByUserPK(String userPK) {
     //   securityContext.getUserPrincipal().getName();
     /*   jwt.getClaimNames().forEach(
                claimName -> System.out.println(claimName + " : " + jwt.getClaim(claimName))
        );*/
     return Response.ok()
        .entity(subscriptionRepository.findByUserPk(userPK))
        .build();
    }
}

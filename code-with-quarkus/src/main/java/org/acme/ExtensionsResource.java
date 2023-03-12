package org.acme;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;
import java.util.concurrent.CompletionStage;

//http://localhost:8080/extension/id/io.quarkus:quarkus-rest-client-reactive
@Path("/extension")
public class ExtensionsResource {

    @RestClient
    ExtensionsService extensionsService;


    @GET
    @Path("/id/{id}")
    public Set<Extension> id(String id) {
        return extensionsService.getById(id);
    }

    @GET
    @Path("/id-async/{id}")
    public CompletionStage<Set<Extension>> idAsync(String id) {
        return extensionsService.getByIdAsync(id);
    }


    @GET
    @Path("/id-uni/{id}")
    public Uni<Set<Extension>> idUni(String id) {
        return extensionsService.getByIdAsUni(id)
                .onFailure()
                .retry()
                .atMost(10);
    }

}
package academy.mindera.quarkus_aws.microservices.user;


import academy.mindera.quarkus_aws.microservices.common.dynamodb.DynamoDbGenericRepository;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.EntityType;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.List;


@ApplicationScoped
public class UserRepository {

    private final DynamoDbGenericRepository<User> userDynamoDynamoDbGenericRepository;
    DynamoDbEnhancedClient enhancedClient;

    @Inject
    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;

        userDynamoDynamoDbGenericRepository = new DynamoDbGenericRepository<>(enhancedClient, User.class);
    }

    public void save(User user) {
        userDynamoDynamoDbGenericRepository.create(user);
    }

    public void saveAll(List<User> users) {
        userDynamoDynamoDbGenericRepository.saveAll(users);
    }


    public User findByPk(String pk) {
        return userDynamoDynamoDbGenericRepository.findByPkAndPartialSk(pk, EntityType.USER.prefixSK())
                .stream().findAny()
                .orElseThrow(
                        WebApplicationException::new
                );
    }

    public List<User> findAll() {
        return userDynamoDynamoDbGenericRepository.findAll(EntityType.USER);
    }
}

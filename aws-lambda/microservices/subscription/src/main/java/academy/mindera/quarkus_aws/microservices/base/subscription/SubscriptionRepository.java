package academy.mindera.quarkus_aws.microservices.base.subscription;

import academy.mindera.quarkus_aws.microservices.common.dynamodb.DynamoDbGenericRepository;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.EntityType;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class SubscriptionRepository {


    private final DynamoDbGenericRepository<Subscription> subscriptionDynamoDynamoDbGenericRepository;
    DynamoDbEnhancedClient enhancedClient;

    @Inject
    public SubscriptionRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;

        subscriptionDynamoDynamoDbGenericRepository = new DynamoDbGenericRepository<>(enhancedClient, Subscription.class);
    }

    public Subscription save(Subscription subscription) {
      return  subscriptionDynamoDynamoDbGenericRepository.create(subscription);
    }

    public void saveAll(List<Subscription> subscriptions) {

        subscriptionDynamoDynamoDbGenericRepository.saveAll(subscriptions);
    }

    public Subscription update(Subscription subscription) {
        return subscriptionDynamoDynamoDbGenericRepository.update(subscription);
    }

    public void delete(Subscription subscription) {
        subscriptionDynamoDynamoDbGenericRepository.delete(subscription.getPK(), subscription.getSK());
    }

    public Subscription findByPk(String pk) {
        return subscriptionDynamoDynamoDbGenericRepository.findByPkAndPartialSk(pk, EntityType.SUBSCRIPTION.prefixSK())
                .stream().findAny()
                .orElseThrow(
                        WebApplicationException::new
                );
    }

    public Subscription findByUserPk(String userPK) {
        return subscriptionDynamoDynamoDbGenericRepository
                .findByPkAndPartialSk(
                        EntityType.SUBSCRIPTION.prefixSK(),
                        EntityType.SUBSCRIPTION.prefixSK() + userPK)
                .stream().findAny()
                .orElseThrow(
                        WebApplicationException::new
                );
    }

    public List<Subscription> findAll() {
        return subscriptionDynamoDynamoDbGenericRepository.findAll(EntityType.SUBSCRIPTION);
    }
}

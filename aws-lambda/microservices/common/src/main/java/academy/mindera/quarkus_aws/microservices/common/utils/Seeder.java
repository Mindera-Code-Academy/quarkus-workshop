package academy.mindera.quarkus_aws.microservices.common.utils;

import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
@UnlessBuildProfile("prod")
public class Seeder {


    @Inject
    DynamoDbClient dynamoDbClient;


    final String tableName =  ConfigProvider.getConfig().getValue("academy.mindera.tableName", String.class);

    public void autoLoad(@Observes StartupEvent ev) {
        if (createTable()) {

            System.out.println("Table created");
        } else {
            System.out.println("Table already exists");
        }
        //usersLoader.loadUsers();

    }

    public boolean createTable() {

        try {
            dynamoDbClient.describeTable(builder -> builder.tableName(tableName));

        } catch (Exception e) {
            dynamoDbClient.createTable(CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(
                            KeySchemaElement.builder()
                                    .attributeName("PK")
                                    .keyType(KeyType.HASH)
                                    .build(),
                            KeySchemaElement.builder()
                                    .attributeName("SK")
                                    .keyType(KeyType.RANGE)
                                    .build()
                    )
                    .attributeDefinitions(
                            AttributeDefinition.builder()
                                    .attributeName("PK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("SK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("GSI1PK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("GSI1SK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("GSI2PK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("GSI2SK")
                                    .attributeType(ScalarAttributeType.S)
                                    .build()


                    )
                    .globalSecondaryIndexes(
                            createGlobalSecondaryIndex("GSI1PK", "GSI1SK"),
                            createGlobalSecondaryIndex("GSI2PK", "GSI2SK")

                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build());

            dynamoDbClient.updateTimeToLive(UpdateTimeToLiveRequest.builder()
                    .tableName(tableName)
                    .timeToLiveSpecification(TimeToLiveSpecification.builder()
                            .enabled(true)
                            .attributeName("TTL")
                            .build())
                    .build());

            return true;
        }
        return false;
    }

    private GlobalSecondaryIndex createGlobalSecondaryIndex(String indexPK, String indexSK) {
        return GlobalSecondaryIndex.builder()
                .indexName(indexPK + "_" + indexSK)
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName(indexPK)
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName(indexSK)
                                .keyType(KeyType.RANGE)
                                .build()
                )
                .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(1L)
                        .writeCapacityUnits(1L)
                        .build())
                .build();
    }

}

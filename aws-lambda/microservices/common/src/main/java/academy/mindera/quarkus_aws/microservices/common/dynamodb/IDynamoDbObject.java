package academy.mindera.quarkus_aws.microservices.common.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.UUID;

public interface IDynamoDbObject {

    @DynamoDbPartitionKey
    default String getPK() {
        return getEntityType().prefixPK() + UUID.randomUUID();
    }


    @DynamoDbSortKey
    default String getSK() {
        return getEntityType().prefixSK() + Instant.now().toEpochMilli();
    }


    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI1PK_GSI1SK"})
    default String getGSI1PK() {
        return getEntityType().prefixPK();
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI1PK_GSI1SK"})
    default String getGSI1SK() {
        return getEntityType().prefixPK() + Instant.now().toEpochMilli();
    }


    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI2PK_GSI2SK"})
    default String getGSI2PK() {
        return getEntityType().prefixPK();
    }


    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI2PK_GSI2SK"})
    default String getGSI2SK() {
        return getEntityType().prefixSK();
    }


    EntityType getEntityType();

}

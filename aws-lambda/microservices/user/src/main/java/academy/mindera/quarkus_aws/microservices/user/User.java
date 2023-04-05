package academy.mindera.quarkus_aws.microservices.user;



import academy.mindera.quarkus_aws.microservices.common.dynamodb.DynamoDbObject;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.EntityType;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.IDynamoDbObject;
import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value;

import java.time.Instant;
import java.util.UUID;

@RegisterForReflection
@DynamoDbBean
public class User extends DynamoDbObject implements IDynamoDbObject {

    String email;


    public User() {
    }

    public User(String email
    ) {

        PK = EntityType.USER.prefixPK() + UUID.randomUUID();
        SK = EntityType.USER.prefixSK() + email;
        GSI1PK = EntityType.USER.prefixPK();
        GSI1SK = EntityType.USER.prefixSK() + email;
        GSI2PK = EntityType.USER.prefixPK();
        GSI2SK = EntityType.USER.prefixSK() + Instant.now().atZone(java.time.ZoneId.of("UTC")).toInstant().toEpochMilli();
        entityType = EntityType.USER;
        this.email = email;
    }

    public User(String email, String UUID)
     {

        PK = EntityType.USER.prefixPK() + UUID;
        SK = EntityType.USER.prefixSK() + email;
        GSI1PK = EntityType.USER.prefixPK();
        GSI1SK = EntityType.USER.prefixSK() + email;
        GSI2PK = EntityType.USER.prefixPK();
        GSI2SK = EntityType.USER.prefixSK() + Instant.now().atZone(java.time.ZoneId.of("UTC")).toInstant().toEpochMilli();
        entityType = EntityType.USER;
        this.email = email;
    }

    public User(
            String PK,
            String SK,
            String GSI1PK,
            String GSI1SK,
            String GSI2PK,
            String GSI2SK,
            String email
    ) {

        this.PK = PK;
        this.SK = SK;
        this.GSI1PK = GSI1PK;
        this.GSI1SK = GSI1SK;
        this.GSI2PK = GSI2PK;
        this.GSI2SK = GSI2SK;
        this.entityType = EntityType.USER;
        this.email = email;

    }

    public record UserCreateDto(String email, String UUID){
        public User toUser() {
            return new User(email, UUID);
        }
    }


    @DynamoDbPartitionKey
    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    @DynamoDbSortKey
    public String getSK() {
        return SK;
    }

    public void setSK(String SK) {
        this.SK = SK;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI1PK_GSI1SK"})
    public String getGSI1PK() {
        return GSI1PK;
    }


    public void setGSI1PK(String GSI1PK) {
        this.GSI1PK = GSI1PK;
    }

    @DynamoDbSecondarySortKey(indexNames = {"GSI1PK_GSI1SK"})
    public String getGSI1SK() {
        return GSI1SK;
    }

    public void setGSI1SK(String GSI1SK) {
        this.GSI1SK = GSI1SK;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"GSI2PK_GSI2SK"})
    public String getGSI2PK() {
        return GSI2PK;
    }

    public void setGSI2PK(String GSI2PK) {
        this.GSI2PK = GSI2PK;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}

package academy.mindera.quarkus_aws.microservices.base.subscription;



import academy.mindera.quarkus_aws.microservices.common.dynamodb.DynamoDbObject;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.EntityType;
import academy.mindera.quarkus_aws.microservices.common.dynamodb.IDynamoDbObject;
import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RegisterForReflection
@DynamoDbBean
public class Subscription extends DynamoDbObject implements IDynamoDbObject {

    String userPK;

    //starting date
    Instant startDate;

    //ending date
    Instant endDate;

    public Subscription() {
    }

    public record SubscriptionDto(String userPK){
        public Subscription toSubscription(){
            return new Subscription(userPK);
        }
    }

    public Subscription(String userPK
    ) {
        PK = EntityType.SUBSCRIPTION.prefixPK();
        SK = EntityType.SUBSCRIPTION.prefixSK() + userPK;
        GSI1PK = EntityType.SUBSCRIPTION.prefixPK();
        GSI1SK = EntityType.SUBSCRIPTION.prefixSK() + Instant.now().atZone(java.time.ZoneId.of("UTC")).toInstant().toEpochMilli();
        GSI2PK = userPK;
        GSI2SK = EntityType.SUBSCRIPTION.prefixSK() + Instant.now().atZone(java.time.ZoneId.of("UTC")).toInstant().toEpochMilli();
        entityType = EntityType.SUBSCRIPTION;
        this.userPK = userPK;
        startDate = Instant.now();
        endDate = Instant.now().plus(30, ChronoUnit.DAYS);
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

    public String getUserPK() {
        return userPK;
    }

    public void setUserPK(String userPK) {
        this.userPK = userPK;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}

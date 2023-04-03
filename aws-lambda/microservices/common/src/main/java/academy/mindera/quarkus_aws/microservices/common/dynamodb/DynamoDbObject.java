package academy.mindera.quarkus_aws.microservices.common.dynamodb;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;

@RegisterForReflection
public abstract class DynamoDbObject {
    public String GSI2PK;
    public String GSI2SK;
    public EntityType entityType;
    public String PK;
    public String SK;
    public String GSI1PK;
    public String GSI1SK;

    // Constructors, getters, and setters

    public DynamoDbObject() {
    }

    public DynamoDbObject(String PK, String SK, EntityType entityType) {
        this.PK = PK;
        this.SK = SK;
        this.GSI1PK = entityType.prefixPK();
        this.GSI1SK = entityType.prefixPK() + Instant.now().toEpochMilli();
        this.entityType = entityType;
    }

    public DynamoDbObject(String PK, String SK, String GSI1SK, EntityType entityType) {
        this.PK = PK;
        this.SK = SK;
        this.GSI1PK = entityType.prefixPK();
        this.GSI1SK = GSI1SK;
        this.entityType = entityType;
    }

    public DynamoDbObject(String PK, String SK, String GSI1SK, String GSI2PK, String GSI2SK, EntityType entityType) {
        this.PK = PK;
        this.SK = SK;
        this.GSI1PK = entityType.prefixPK();
        this.GSI1SK = GSI1SK;
        this.GSI2PK = GSI2PK;
        this.GSI2SK = GSI2SK;
        this.entityType = entityType;
    }


    public EntityType getEntityType() {
        return entityType;
    }


    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }


}


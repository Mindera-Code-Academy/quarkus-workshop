package academy.mindera.quarkus_aws.microservices.common.dynamodb;

public enum EntityType {

    USER("USER#", "USER#", "User"),

    SUBSCRIPTION("SUBSCRIPTION#", "SUBSCRIPTION#", "Subscription");

    private final String prefixPK;
    private final String prefixSK;
    private final String description;

    EntityType(String prefixPK, String prefixSK, String description) {
        this.prefixPK = prefixPK;
        this.prefixSK = prefixSK;
        this.description = description;
    }

    public String prefixPK() {
        return prefixPK;
    }

    public String prefixSK() {
        return prefixSK;
    }

    public String description() {
        return description;
    }
}

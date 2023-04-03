package academy.mindera.quarkus_aws.microservices.common.dynamodb;


import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class DynamoDbGenericRepository<T> {


    DynamoDbEnhancedClient enhancedClient;

    Class<T> clazz;
    TableSchema<T> tableSchema;
    DynamoDbTable<T> table;
    String tableName;

    public DynamoDbGenericRepository(DynamoDbEnhancedClient enhancedClient, Class<T> clazz) {
        this.clazz = clazz;
        this.enhancedClient = enhancedClient;
        this.tableSchema = TableSchema.fromBean(clazz);
        this.tableName = ConfigProvider.getConfig().getValue("academy.mindera.tableName", String.class);
        this.table = enhancedClient.table(tableName, tableSchema);
    }

    public List<T> findAll(EntityType entityType) {
        return findByGsi(
                "GSI1PK_GSI1SK",
                entityType.prefixPK(),
                entityType.prefixSK(),
                null,
                null);

    }

    public T create(T item) {
        table.putItem(
                PutItemEnhancedRequest.builder(clazz)
                        .conditionExpression(
                                Expression.builder()
                                        .expression("attribute_not_exists(#PK) AND attribute_not_exists(#SK)")
                                        .expressionNames(Map.of("#PK", "PK", "#SK", "SK"))

                                        .build()
                        )
                        .item(item)
                        .build()
        );
        return item;
    }


    public List<T> saveAll(List<T> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        List<WriteBatch> batches = new ArrayList<>();

        // Split the items into chunks of 25 the max batch size defined by DynamoDB
        for (int i = 0; i < items.size(); i += 25) {
            List<T> chunk = items.subList(i, Math.min(i + 25, items.size()));
            WriteBatch.Builder<T> batchBuilder = WriteBatch.builder(clazz)
                    .mappedTableResource(table);
            chunk.forEach(batchBuilder::addPutItem);
            batches.add(batchBuilder.build());
        }

        for (WriteBatch batch : batches) {
            enhancedClient.batchWriteItem(r -> r.addWriteBatch(batch));
        }

        return items;
    }


    public T update(T item) {
        table.updateItem(item);
        return item;
    }

    public void delete(String pk, String sk) {
        Key key = Key.builder().partitionValue(pk).sortValue(sk).build();
        table.deleteItem(key);
    }

    public Optional<T> findById(String pk, String sk) {
        Key key = Key.builder().partitionValue(pk).sortValue(sk).build();
        return Optional.of(table.getItem(key));
    }

    public List<T> findByPkAndPartialSk(String pk, String partialSk) {

        QueryConditional queryConditional = QueryConditional.sortBeginsWith(
                Key.builder()
                        .partitionValue(pk)
                        .sortValue(partialSk)
                        .build());

        return table.query(r -> r.queryConditional(queryConditional))
                .stream()
                .flatMap(p -> p.items().stream()).toList();
    }


    public List<T> findByGsi(
            String gsiName,
            String gsiPkValue,
            String gsiSkValuePrefix,
            String filterExpression,
            Map<String, AttributeValue> expressionAttributeValues) {
        DynamoDbIndex<T> index = table.index(gsiName);
        QueryConditional queryConditional = QueryConditional.sortBeginsWith(
                Key.builder()
                        .partitionValue(gsiPkValue)
                        .sortValue(gsiSkValuePrefix)
                        .build());

        if (filterExpression == null || expressionAttributeValues == null) {
            return index.query(r -> r.queryConditional(queryConditional))
                    .stream()
                    .flatMap(p -> p.items().stream()).toList();
        } else {
            Expression filter = Expression.builder()
                    .expression(filterExpression)
                    .expressionValues(expressionAttributeValues)
                    .build();

            return index.query(r -> r.queryConditional(queryConditional).filterExpression(filter))
                    .stream()
                    .flatMap(p -> p.items().stream()).toList();
        }
    }


    public String scanAll() {
        return table.scan()
                .items()
                .stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
    }


}

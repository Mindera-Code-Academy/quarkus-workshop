package academy.mindera.quarkus_aws.microservices.subscription_lambda;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProcessingService {

    public String process(List<Map<String, String>> stringStringMap) {
        stringStringMap.forEach(this::processMap);
        return "OK";
    }


    private void processMap(Map<String, String> stringStringMap) {
        String eventName = stringStringMap.get("eventName");
        switch (eventName) {
            case "INSERT" -> System.out.println("INSERT");
            case "MODIFY" -> System.out.println("MODIFY");
            case "REMOVE" -> System.out.println("REMOVE");
            default -> System.out.println(eventName);
        }
    }

}

/**
 *
 * { "eventName": ["REMOVE"], "dynamodb": { "Keys": {  "PK": { "S": [{ "prefix": "SUBSCRIPTION#" }] }, "SK": { "S": [{ "prefix": "USER#" }] } } } }
 */

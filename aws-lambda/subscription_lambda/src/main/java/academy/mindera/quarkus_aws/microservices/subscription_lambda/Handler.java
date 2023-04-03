package academy.mindera.quarkus_aws.microservices.subscription_lambda;

import javax.inject.Inject;
import javax.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;
import java.util.Map;

@Named("handler")
public class Handler implements RequestHandler<List<Map<String, String>>, String> {

    @Inject
    ProcessingService service;



    @Override
    public String handleRequest(List<Map<String, String>> stringStringMap, Context context) {

        if (stringStringMap != null && !stringStringMap.isEmpty()) {
            return service.process(stringStringMap);
        }

        throw new RuntimeException("No data");
      //  return stringStringMap;
    }
}

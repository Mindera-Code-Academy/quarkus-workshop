#!/usr/bin/env bash
# tag::adocSnippet[]
mvn archetype:generate \
       -DarchetypeGroupId=io.quarkus \
       -DarchetypeArtifactId=quarkus-amazon-lambda-archetype \
       -DarchetypeVersion=2.16.6.Final \
       -DprojectGroupId=academy.mindera.quarkus_aws.microservices \
       -DprojectArtifactId=subscription_lambda \
       -DprojectVersion=1.0.0-SNAPSHOT \
      # -DclassName="academy.mindera.quarkus_aws.microservices.subscription_lambda.SubscriptionEventReceiver" \
   # -Dpath="/api/subscriptions" \
   # -Dextensions="resteasy-jsonb, rest-client, smallrye-fault-tolerance, smallrye-metrics"
# end::adocSnippet[]
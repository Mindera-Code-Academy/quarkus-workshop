#!/usr/bin/env bash
# tag::adocSnippet[]
mvn io.quarkus:quarkus-maven-plugin:3.0.0.CR1:create \
    -DplatformVersion=3.0.0.CR1 \
    -DprojectGroupId=academy.mindera.quarkus_aws.microservices.base.user \
    -DprojectArtifactId=user \
    -DprojectVersion=1.0.0-SNAPSHOT \
    -DclassName="academy.mindera.quarkus_aws.microservices.base.user.UserResource" \
    -Dpath="/api/users" \
   # -Dextensions="resteasy-jsonb, rest-client, smallrye-fault-tolerance, smallrye-metrics"
# end::adocSnippet[]
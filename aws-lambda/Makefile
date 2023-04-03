run-subscription-native:
	@echo "Running subscription native"
	#@sam local invoke -e events/subscription-event.json -t template.yaml SubscriptionFunction
	./microservices/subscription/target/subscription-1.0.0-SNAPSHOT-runner

build-native-with-dockerx:
	@echo "Building native with docker"
	@docker run --rm -v $(PWD):/project -w /project quay.io/quarkus/centos-quarkus-maven:20.1.0-java11 \
	./mvnw package -Pnative -Dquarkus.native.container-build=true

run-subscription-lambda:
	@echo "Running subscription lambda"
	@sam local invoke -e ./microservices/subscription_lambda/payload.json -t ./microservices/subscription_lambda/target/sam.native.yaml
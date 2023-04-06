run-subscription-native:
	@echo "Running subscription native"
	#@sam local invoke -e events/subscription-event.json -t template.yaml SubscriptionFunction
	./microservices/subscription/target/subscription-1.0.0-SNAPSHOT-runner

build-native-with-dockerx:
	@echo "Building native with docker"
	@docker run --rm -v $(PWD):/project \
	--platform=linux/arm64 \
 	-w /project quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 \
	bash -c "./mvnw package -Pnative"

build-quarkus-training:
	docker run -it --rm \
		  --platform=linux/arm64 \
		  -v $(CURDIR):/project \
		  -v $(CURDIR)/target:/project/target \
		  -w /project \
		  --entrypoint /bin/bash \
		  quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 \
		  -c "./mvnw install -Dnative -DskipTests"



run-subscription-lambda:
	@echo "Running subscription lambda"
	@sam local invoke -e ./microservices/subscription_lambda/payload.json -t ./microservices/subscription_lambda/target/sam.native.yaml
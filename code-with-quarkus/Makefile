run-native:
	./target/code-with-quarkus-1.0.0-SNAPSHOT-runner

run-docker:
	docker build -f ./src/main/docker/Dockerfile.jvm -t academy.mindera/docker .
	docker run -i --rm -p 8080:8080 academy.mindera/docker

run-docker-native:
	#mvn wrapper:wrapper
	#mvn package -Pnative -Dmaven.test.skip=true
	docker build -f ./src/main/docker/Dockerfile.native -t academy.mindera/docker .
	docker run -i --rm -p 8080:8080 academy.mindera/docker


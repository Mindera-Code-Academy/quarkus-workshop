run-native:
	./target/local-agent-1.0.0-SNAPSHOT-runner

run-docker:
	docker build -f Dockerfile.jvm -t minka-code/local-agent .
	docker run -i --rm -p 8080:8080 minka-code/local-agent

run-docker-native:
	#mvn wrapper:wrapper
	#mvn package -Pnative -Dmaven.test.skip=true
	docker build -f Dockerfile.native -t minka-code/local-agent .
	docker run -i --rm -p 8080:8080 minka-code/local-agent
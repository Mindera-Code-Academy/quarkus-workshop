

 deploy-quarkus-training:
	sam build --template-file ./template.yaml \
	&& \
	sam deploy --template-file ./template.yaml \
	--stack-name quarkus-training \
	--capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM CAPABILITY_AUTO_EXPAND \
	--region us-east-2 \
	--resolve-s3 \
	--profile mindera-code-challenge-god

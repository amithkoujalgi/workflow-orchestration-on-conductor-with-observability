build:
	mvn -U --file pom.xml clean install -DskipTests=true -Dmaven.javadoc.skip=true

build-test:
	mvn -U clean verify install package --file pom.xml

add-workflow:
	cd ./deploy && bash setup-tasks-and-workflow.sh && cd -

start-worker:
	mvn clean install spring-boot:run

start-docker:
	docker-compose -f ./deploy/docker-compose.yaml down -v; \
    docker-compose -f ./deploy/docker-compose.yaml rm -fsv; \
    docker-compose -f ./deploy/docker-compose.yaml up --remove-orphans;

stop-docker:
	docker-compose -f ./deploy/docker-compose.yaml down -v; \
    docker-compose -f ./deploy/docker-compose.yaml rm -fsv;

exec-workflow:
	curl 'http://localhost:5000/api/workflow' \
      -H 'Content-Type: application/json' \
      --data-raw '{"name":"AddAndSquareNumbersFlow","version":"1","input":{"num1":10,"num2":20}}' \
      --compressed \
      --silent \
      --insecure

exec-workflow-faulty:
		curl 'http://localhost:5000/api/workflow' \
          -H 'Content-Type: application/json' \
          --data-raw '{"name":"AddAndSquareNumbersFlow","version":"1","input":{"num1":"10","num2":20}}' \
          --compressed \
          --silent \
          --insecure
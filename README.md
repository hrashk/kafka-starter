### build the project and run its tests
```bash
./mvnw clean package
```

### install the starter library locally (and skip the tests)
```bash
./mvnw -f kafka-starter/pom.xml -DskipTests install
```

### run the producer and consumer examples in two separate terminals

First, start the docker containers
```bash
docker compose -f docker/docker-compose.yml up -d
```

Then run the producer in a separate terminal window
```bash
java -jar example-producer/target/example-producer-1.0-SNAPSHOT.jar
```

Then the consumer, also in a separate terminal
```bash
java -jar example-consumer/target/example-consumer-1.0-SNAPSHOT.jar
```

Press Ctrl-C to stop the processes.

Then stop docker containers
```bash
docker compose -f docker/docker-compose.yml down
```


### change configuration

Observe how IDE provides completion recommendations of the new properties from the starter library
when editing application.yml file.

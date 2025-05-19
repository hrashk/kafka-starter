### Installing the starter library locally

This project was built with JDK 21 and maven 3.9. Later versions should also work.

```bash
./mvnw -f kafka-starter/pom.xml -DskipTests clean install
```

### Building all project modules along with running the tests
```bash
./mvnw clean package
```

### Running examples

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


### Configuration

One can change configuration in application.yml files and observe their effect. 

Notice how IDE provides completion recommendations of the new properties from the starter library
when editing application.yml file.

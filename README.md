### build the project and run its tests
```bash
./mvnw clean package
```


### install the starter library locally
```bash
./mvnw -f kafka-starter/pom.xml install
```

### run the producer and consumer examples in two separate terminals

First, start the docker containers

Then run the producer

And finally the consumer

Press Ctrl-C to stop the processes.

### change configuration

Observe how IDE provides completion recommendations of the new properties from the starter library
when editing application.yml file.

# routeplanner

Multimodule project with two Spring Boot applications.

1. Run MongoDB using Docker and change volume path, user password and root password to something meaningful:

```
docker run --name mongodb -p 127.0.0.1:27017:27017 -v <PATH_TO_WRITABLE_DIRECTORY>:/bitnami/mongodb -e MONGODB_USERNAME=user -e MONGODB_PASSWORD=<USER_PASSWORD> -e MONGODB_DATABASE=timetable_planner -e MONGODB_ROOT_PASSWORD=<ROOT_PASSWORD> bitnami/mongodb:4.4
```

2. Change application properties to match previously set mongodb password and set mqtt settings for integration application
   - routeplanner-web/src/main/resources/application.properties
   - routeplanner-integration/src/main/resources/application.properties

3. Build project with Maven wrapper (included):

`./mvnw clean package`

4. Run MQTT integration application:

`java -jar routeplanner-integration/target/routeplanner-integration-0.0.1-SNAPSHOT.jar`

5. Run REST-API application:

`java -jar routeplanner-web/target/web-0.0.1-SNAPSHOT.jar`

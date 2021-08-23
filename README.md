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

## Testing the API

### Get all distinct lines you have collected data for

`curl localhost:8080/routes/`

Example response:

```json
[
    {
        "direction": "1",
        "line": "1",
        "startTime": "05:26"
    },
    {
        "direction": "1",
        "line": "1",
        "startTime": "05:37"
    },
    {
        "direction": "1",
        "line": "1",
        "startTime": "05:38"
    }
]
```

### Get aggregated statistics for a single route (combination of line, direction and startTime)

```
curl "localhost:8080/routes/statistics?line=4&direction=2&startTime=15:55&queryFromDate=2021-08-18&queryToDate=2021-08-23"
```

Example response:

```json
[
    {
        "averageTime": "15:49:26",
        "maxTime": "15:50:29",
        "minTime": "15:48:23",
        "standardDeviation": 63,
        "stop": "1301456",
        "targetTime": "15:55:00"
    },
    {
        "averageTime": "15:55:34",
        "maxTime": "15:55:34",
        "minTime": "15:55:34",
        "standardDeviation": 0,
        "stop": "1301455",
        "targetTime": "15:56:00"
    },
    {
        "averageTime": "15:56:33",
        "maxTime": "15:56:47",
        "minTime": "15:56:19",
        "standardDeviation": 14,
        "stop": "1301453",
        "targetTime": "15:57:00"
    },
    {
        "averageTime": "15:57:31",
        "maxTime": "15:57:51",
        "minTime": "15:57:12",
        "standardDeviation": 19,
        "stop": "1301451",
        "targetTime": "15:58:00"
    }
    ...
```

#build stage
FROM maven:3.6.3-jdk-11-slim as build-stage
COPY . strava-client-comments
WORKDIR strava-client-comments
RUN mvn package -DskipTests -e
RUN ls
WORKDIR target
RUN ls

#production stage
FROM openjdk:8-jdk-alpine as production-stage
RUN ls
COPY --from=build-stage /strava-client-comments/target/client-comments-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

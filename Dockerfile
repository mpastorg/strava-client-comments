#build stage
FROM maven:3.6.3-jdk-11-slim as build-stage
COPY . strava-client-comments
WORKDIR strava-client-comments
RUN rm -rf src/main/resources/application.yaml
RUN mvn package -DskipTests -e
RUN ls
WORKDIR target
RUN ls

#production stage
FROM adoptopenjdk:11-jre-hotspot as production-stage
RUN ls
COPY --from=build-stage /strava-client-comments/target/client-comments-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

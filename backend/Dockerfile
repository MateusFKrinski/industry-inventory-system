FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 AS build

WORKDIR /code

COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml

RUN ./mvnw -B dependency:go-offline

COPY src src

RUN ./mvnw package -DskipTests

FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:1.14

WORKDIR /work

COPY --from=build /code/target/quarkus-app/ /work/

EXPOSE 8080

CMD ["java", "-jar", "/work/quarkus-app.jar"]


FROM maven:3.9.6-eclipse-temurin-22-jammy AS build
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:22-jdk-jammy
COPY --from=build /target/Hotel-3.3.0-SNAPSHOT.jar Hotel.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Hotel.jar"]

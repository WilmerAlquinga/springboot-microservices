FROM openjdk:17

EXPOSE 8080

WORKDIR /app

COPY src/main/resources/application-docker.properties /app/application-docker.properties
COPY target/clients-service-1.0.0.jar app.jar

CMD ["java", "-jar", "/app/app.jar"]

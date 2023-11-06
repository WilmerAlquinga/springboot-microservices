FROM openjdk:17

EXPOSE 9090

WORKDIR /app

COPY src/main/resources/application-docker.properties /app/application-docker.properties
COPY target/accounts-service-1.0.0.jar app.jar

CMD ["java", "-jar", "/app/app.jar"]

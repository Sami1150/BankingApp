FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY target/banking-app.jar banking-app.jar
ENTRYPOINT ["java", "-jar", "banking-app.jar"]

FROM openjdk:17
EXPOSE:8080
ADD banking-api/target/banking-app.jar banking-app.jar
ENTRYPOINT ["java","-jar","/banking-app.jar"]
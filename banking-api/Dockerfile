FROM openjdk:17
#VOLUME /tmp
EXPOSE 8080
ADD target/banking-app.jar banking-app.jar
ENTRYPOINT ["java","-jar","/banking-app.jar"]
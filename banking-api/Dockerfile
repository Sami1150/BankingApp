FROM openjdk:17
#VOLUME /tmp
# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/banking-app.jar /app

EXPOSE 8080
#ADD target/banking-app.jar banking-app.jar
ENTRYPOINT ["java","-jar","/banking-app.jar"]
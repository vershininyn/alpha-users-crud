FROM eclipse-temurin:17-jdk-alpine

MAINTAINER vershininyn

RUN mkdir /opt/app
COPY ./alpha-users-crud.jar /opt/app

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/alpha-users-crud.jar"]





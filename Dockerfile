FROM adoptopenjdk/openjdk11
LABEL maintainer="justinkim1201@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/nosql-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} spring-app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-app.jar"]
FROM amazoncorretto:8
VOLUME /tmp
COPY build/libs/project61-0.0.1-SNAPSHOT.jar project61.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/project61.jar"]
EXPOSE 9090
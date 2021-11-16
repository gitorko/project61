FROM openjdk:11
WORKDIR /var/app
ADD build/libs/project61-1.0.0.jar /var/app/project61-1.0.0.jar
ENTRYPOINT [ "java", "-jar", "/var/app/project61-1.0.0.jar" ]
EXPOSE 8080

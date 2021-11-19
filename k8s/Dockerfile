FROM openjdk:11
WORKDIR /var/app
COPY ./build/libs/project61-1.0.0.jar /var/app/project61-1.0.0.jar
ENV _JAVA_OPTIONS '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005'
CMD [ "java", "-jar", "/var/app/project61-1.0.0.jar" ]
EXPOSE 9090 5005

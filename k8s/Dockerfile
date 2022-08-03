FROM openjdk:17
WORKDIR /var/app
COPY ./build/libs/project61-1.0.0.jar /var/app/project61-1.0.0.jar
ENV _JAVA_OPTIONS '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005'
CMD [ "java", \
"-Dcom.sun.management.jmxremote=true", \
"-Dcom.sun.management.jmxremote.port=9095", \
"-Dcom.sun.management.jmxremote.local.only=false", \
"-Dcom.sun.management.jmxremote.authenticate=false", \
"-Dcom.sun.management.jmxremote.ssl=false", \
"-Dcom.sun.management.jmxremote.rmi.port=9095", \
"-Djava.rmi.server.hostname=localhost", \
"-jar", "/var/app/project61-1.0.0.jar" ]
EXPOSE 9090
EXPOSE 5005
EXPOSE 9095

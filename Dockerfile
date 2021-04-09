FROM adoptopenjdk:15_36-jdk-hotspot
VOLUME /tmp
EXPOSE 8080
ADD target/themyscira-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

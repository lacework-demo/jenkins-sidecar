FROM maven AS builder
COPY . /home/maven/src
WORKDIR /home/maven/src
RUN mvn clean package

FROM openjdk:8
EXPOSE 8080
RUN mkdir /app
WORKDIR /app
COPY src/main/resources/hibernate.cfg.xml /app/config/hibernate.cfg.xml
COPY --from=builder /home/maven/src/target/jenkins-sidecar-1.0-SNAPSHOT-jar-with-dependencies.jar /app/backend-reporter-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD ["java", "-jar", "/app/backend-reporter-1.0-SNAPSHOT-jar-with-dependencies.jar"]
#tinieuser_otp_gen DEPLOYMENT INSTRUCTIONS

To deploy this app, build the jar first and then run using java cmd or via docker.

**Requirements**:
- [Gradle 7.4.2](https://gradle.org/releases/).
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## BUILDING JAR
- Initialise terminal window and navigate to this directory
- Execute the following commands:

      cd otp-gen
      ./gradlew bootJar

- Find "otp-gen-0.0.1-SNAPSHOT.jar" in build/libs directory

## EXECUTING JAR USING JAVA
- Initialise terminal window and navigate to jar file location
- Set env variable for TEXTLOCAL_API_KEY
- Execute:

      java -jar otp-gen-0.0.1-SNAPSHOT.jar

## EXECUTING JAR USING DOCKERFILE
- Navigate to **otp-gen** directory
- Create Dockerfile with the following content and replace placeholders:

      FROM openjdk:17.0.1-jdk
      ARG JAR_FILE=build/libs/otp-gen-0.0.1-SNAPSHOT.jar
      ARG TEXTLOCAL_API_KEY=<database url>
      COPY ${JAR_FILE} app.jar
      ENTRYPOINT ["java","-jar","/app.jar"]

- Run the dockerfile with the following command:

      docker build -t springio/gs-spring-boot-docker .

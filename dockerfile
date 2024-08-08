# Use the official maven/Java 8 image to create a build artifact.
# This is based on Debian and sets the MAVEN_HOME environment variable to /usr/share/maven
FROM maven:3.8.2-openjdk-8-slim AS build

# Copy the settings.xml file
COPY settings.xml /usr/share/maven/ref/

# Copy the pom.xml file to download dependencies
COPY pom.xml /usr/src/app/
WORKDIR /usr/src/app/
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src /usr/src/app/src
RUN mvn package

# Use OpenJDK to run the application
FROM openjdk:8-jre-slim

# Copy the jar file built in the first stage
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

# Set the working directory
WORKDIR /usr/app

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

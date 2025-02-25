# Use the Eclipse alpine official image
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:21-jdk-alpine
# Create and change to the app directory.
WORKDIR /app
# Copy files to the container image
# COPY ./backend/ecommerce-spring-boot/.mvn/ .mvn
# COPY ./backend/ecommerce-spring-boot/mvnw ./
# COPY ./backend/ecommerce-spring-boot/pom.xml/ ./
COPY ./backend/ecommerce-spring-boot/target/*.jar  app.jar
# RUN ./mvnw dependency:go-offline
# COPY ./backend/ecommerce-spring-boot/src ./src
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
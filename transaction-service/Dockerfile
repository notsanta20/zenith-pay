FROM eclipse-temurin:22-jdk-alpine
ADD target/transaction-service.jar transaction-service.jar
ENTRYPOINT ["java", "-jar", "/transaction-service.jar"]
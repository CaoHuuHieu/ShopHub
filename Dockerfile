FROM maven:3.9.6-eclipse-temurin-21-jammy

WORKDIR /shop_hub
COPY . .
RUN mvn clean package
CMD ["java" , "-jar",  "target/shop_hub-0.0.1-SNAPSHOT.jar"]






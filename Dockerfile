
# add your tools here ...
FROM maven:3.8-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
# EXPOSE 8080
EXPOSE 8086
#ADD target/retail-rocket-0.0.1-SNAPSHOT.jar retail-rocket-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/retail-rocket-0.0.1-SNAPSHOT.jar retail-rocket-0.0.1-SNAPSHOT.jar
# RUN java -jar /com.simulator.jar --info
ENTRYPOINT ["java","-jar","/app/retail-rocket-0.0.1-SNAPSHOT.jar"]

FROM gradle:7.4.2-jdk17-alpine AS builder

WORKDIR /app

COPY build.gradle.kts ./
COPY gradlew ./
RUN chmod +x gradlew

RUN gradle clean build

FROM openjdk:17-alpine AS app

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar .

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "*.jar"]
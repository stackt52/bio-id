# BioID System

## Introduction

The `BioID` system is designed for capturing, storing, and verifying fingerprint biometrics of `subject's`.
It leverages a microservice architecture to enable efficient scaling and modular deployment. 

## Prerequisites
- Java 17 and above (https://www.oracle.com/java/technologies/downloads/)
- Gradle (https://gradle.org/releases/)
- Docker (https://www.docker.com/products/docker-desktop/)

The system contains the following services:

### 1. API Gateway.
This is the entry point of external traffic to the system. It offers the following features.
- Authentication
- Rate-limiting
- Load-balancing

### 2. [Configuration Server](config-server/README.md)
Manages all system-wide service configurations

### 3. Zipkin Server
[Zipkin](https://hub.docker.com/r/openzipkin/zipkin) is a distributed tracing system. It is being used to gather timing data needed to troubleshoot 
latency problems in the system.
To run Zipkin server make sure you have [docker](https://www.docker.com/products/docker-desktop/) installed on you machine and run the following command:
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```

### 4. Service Registry
Creates a [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) service registry instance that supports auto-client discovery

### 5. [Enrolment Service](enrolment-service/README.md)
The `enrolment service` handles all enrolment operations by providing functionalities for enrolling new clients for 
biometrics fingerprint verification. The information required when enrolling new client a basic client profile, 
fingerprint images, and an auxiliary ID used of identifying 

### 6. [Bio Data Service](bio-data-service/README.md)
The `boi-data service` is the service that is in change of storing the fingerprint images of clients enrolled. It also 
offers efficient caching strategies for efficient retrieval of captured images that are used by the search service.

### 7. [Search Service](search-service/README.md)
The `search service` implements [SourceAFIS](https://sourceafis.machinezoo.com/java) fingerprint verification algorithm for 
identifying for performing 1:1 verification against the probe and stored candidates.

### 8. [Console Service](console-service/README.md)
The `console service` offers functionalities geared for the administration and monitoring of the BioID system. It integrates 
directly with available services through the service discovery and doesn't make called via the API Gateway.

### 9. [Identity Service](identity-service/README.md)
The `identity-service` offers identity management services for the **bio-data-service**. It supports user registration, 
auth token generation and validation.
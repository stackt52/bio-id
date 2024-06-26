# BioID System

## Overview

The `BioID system` offers a centralized digital identity management service. The system supports
the registration of clients' basic demographic information together with their fingerprints.
Once a client has been registered, their identity can be verified from any system through the BioID system.
This introduces an efficient `centralized approach to client profile management` which promotes
coordinated service provision across system boundaries.

#### Challenges the BioID address:
- Siloed client information captured in different systems.
- Duplicate client profiles.
- Ineffective client verification procedures.
- Administrative burden by multiple manual data entry.
- Un-streamlined workflows - spending more time on client management than actual service delivery.
- Provide some background and context, and explain choices and alternatives.

## Architecture

The system has been built on a microservice architecture which enables services to be scaled
independently, allowing for efficient resource utilization and handling of increased loads for
specific components.

Since functionalities are isolated in dedicated services, each service can be efficiently scaled
up in isolation in response to traffic accordingly - leading to optimized performance and
cost-efficiency.

#### System interaction

The logical diagram below depicts the interaction between source systems with the BioID
microservice-based client management system.

![BioID logical diagram](bioid-logical-diagram.svg)

The system exposes three primary services accessible to client applications: Identity,
Enrolment, and Search. All client requests are managed through a single entry
point - the API Gateway, which implements crosscutting concerns such as authentication,
logging, routing, etc.

## Services

### 1. API Gateway.
This is the entry point of external traffic to the system. Offers the following features.
- Authentication
- Routing
- Load-balancing


### 2. Configuration Server.
Manages all system-wide service configurations

### 3. Zipkin Server
[Zipkin](https://hub.docker.com/r/openzipkin/zipkin) is a distributed log tracing system. It is used to gather timing data needed to troubleshoot 
latency challenges in the system.
To run the Zipkin server make sure you have [docker](https://www.docker.com/products/docker-desktop/) installed on your machine and run the following
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```

### 4. Service Registry
Creates a [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) service registry instance that supports auto-service discovery.

### 5. Enrolment Service
The `enrolment service` handles all enrolment operations by providing functionalities for enrolling new clients for 
biometrics fingerprint verification. The information required when enrolling new clients a basic client profile, 
fingerprint images, and an auxiliary ID used to identify 

### 6. Bio Data Service
The `boi-data service` is the service that is in change of storing the fingerprint images of clients enrolled. It also 
offers efficient caching strategies for efficient retrieval of captured images that are used by the search service.

### 7. Search Service
The `search service` implements [SourceAFI](https://sourceafis.machinezoo.com/java) fingerprint verification algorithm for 
identifying for performing 1:1 verification against the probe and stored candidates.

### 8. Console Service
The `console service` offers functionalities geared for the administration and monitoring of the BioID system. It integrates 
directly with available services through the service discovery and doesn't make calls via the API Gateway.

### 9. Identity Service
The `identity-service` offers identity management services for the **bio-data-service**. It supports user registration, 
auth token generation and validation.

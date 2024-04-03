## BioID System

The `BioID` is a system that supports `subject's` fingerprint biometric capturing, storage, and verification.
It is build on a [microservice](https://spring.io/microservices) architecture that allows for efficient scalability and composable deployment 
requirements.

The system contains the following services:

### 1. API Gateway.
This is the entry point of external traffic to the system. Offers the following features.
- Authentication
- Rate-limiting
- Load-balancing


### 2. Configuration Server.
Manages all system-wide service configurations

### 3. Zipkin Server
[Zipkin](https://hub.docker.com/r/openzipkin/zipkin) is a distributed tracing system. It is being used to gather timing data needed to troubleshoot 
latency problems in system.
To run Zipkin server make sure you have [docker](https://www.docker.com/products/docker-desktop/) installed on you machine and run the following
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```

### 4. Service Registry
Creates a [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) service registry instance that support auto-client discovery

### 5. Enrolment Service
The `enrolment service` handles all enrolment operations by providing functionalities for enrolling new clients for 
biometrics fingerprint verification. The information required when enrolling new client a basic client profile, 
fingerprint images, and an auxiliary ID used of identifying 

### 6. Bio Data Service
The `boi-data service` is the service that is in change of storing the fingerprint images of clients enrolled. It also 
offers efficient caching strategies for efficient retrieval of captured images that are used by the search service.

### 7. Search Service
The `search service` implements [SourceAFI](https://sourceafis.machinezoo.com/java) fingerprint verification algorithm for 
identifying for performing 1:1 verification against the probe and stored candidates.

### 8. Console Service
The `console service` offers functionalities geared for the administration and monitoring of the BioID system. It integrates 
directly with available services through the service discovery and doesn't make called via the API Gateway.
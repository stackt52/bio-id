## National Patient Biometric Verification System

The `NPBVS` is a system that supports `subject's` fingerprint biometric capturing, storage, and verification.
It is build on a [microservice](https://spring.io/microservices) architecture that allows for efficient scalability and composable deployment 
requirements.

The system contains the following services:

### API Gateway.
This is the entry point of external traffic to the system. Offers the following features.
- Authentication
- Rate-limiting
- Load-balancing


### Configuration Server.
Manages all system-wide service configurations

### Zipkin Server
[Zipkin](https://hub.docker.com/r/openzipkin/zipkin) is a distributed tracing system. It is being used to gather timing data needed to troubleshoot 
latency problems in system.
To run Zipkin server make sure you have [docker](https://www.docker.com/products/docker-desktop/) installed on you machine and run the following
```shell
docker run -d -p 9411:9411 openzipkin/zipkin
```

### Service Registry
Creates a [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) service registry instance that support auto-client discovery
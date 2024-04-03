# Console Service

An admin tool for managing the `BioID` backend microservices. It offers the following services:
- Monitoring (Enrolments, running microservices) 
- Enrolments management (Add, update, delete)
- Biometrics search.
- Security management.
- Source systems management.

## Front-end

The `front-end` application is a [React](https://react.dev/) web app build with [Material UI](https://mui.com/) 
components. A gradle task has been configured that builds the front-end app and copies the assets into the `spring-boot` 
application's build directory so that it can be packed and deployed as a `jar` file on `Tomcat` / `Netty` server.

## Back-end 

The `back-end` application is a spring-boot microservice configured to access other services via the `discovery` service. 
It exposes `REST API` endpoints that are called from the front-end app to perform certain actions. All calls made 
by this service are routed directly to the corresponding services instance and are never directed through the 
`API Gateway`.

### `Build`

In order to build the console-service (front-end & back-end):
1. Run gradle `./gradlew clean` task.
2. Then run `./gradlew bootJar`. This will trigger the following processes:
   - Download `Node v21` if not yet done. 
   - `npm install` front-end packages if necessary.
   - Create a production ready version of the app.
   - Copy the front-end assets into the back-end service build dir.
   - Create a jar file ready for deployment.
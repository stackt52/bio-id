# Enrolment Service

The `Enrolment Service` manages details for all enrolled subjects. It exposes endpoints for persisting, reading,
updating and deleting subject details.

This service interfaces with postgresql for persistence

## Postgresql Setup

### Docker volume
It is recommended to create a docker volume to persist the postgresql data even if the container is stopped or removed

```shell
docker volume create pgdata
```

### Run postgresql container
To start an instance of postgresql in a docker container run the following command in your terminal:
```shell
docker run -d --name postgres -e POSTGRES_PASSWORD=<password> -p 5432:5432 -v pgdata:/var/lib/postgresql/data postgres
```
The command above also mounts the docker volume created earlier to persist the data.

### Access postgresql
Postgresql can be accessed from your local machine using tools like psql or pgadmin

```shell
psql -h localhost -U postgres -d postgres
```
You'll be prompted to enter the password set up during container creation.

## Database setup
While making use of either psql or pgadmin, create a new database called enrolment. A script defining the schema can be
found in resources/schema.sql
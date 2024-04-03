# Enrolment Service

The `Enrolment Service` manages details for all enrolled subjects. It exposes endpoints for persisting, reading,
updating and deleting subject details.

This service uses a [postgresql](https://www.postgresql.org/) datastore for persistence.

## Features

1. **Subject Details storage and management:** Enables basic CRUD functionalities on subjects and stores subject details including:
- Name
- sex
- Date of birth
- Id type (e.g nupn, nrc etc)
- Id number
2. **System types:** Keeps a log of the different information systems that utilise the bio-id.

## Service Dependencies

- **Search-service:** Sends response to search-service when provided with query
- **Bio-data-service:** Interfaces with the bio-data-service when a client with a given fingerprint is to be registered in the system

## Postgresql setup
The latest postgresql version for all systems can be downloaded [here](https://www.postgresql.org/download/), with the installation steps provided.

### R2DBC
This service utilises the [Reactive Relational Database Connectivity](https://r2dbc.io/) API to make it easy for SQL databases
such as postgresql to take advantage of reactive programming paradigms (i.e. fully non-blocking drivers) used in this project.

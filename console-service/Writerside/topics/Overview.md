# Overview

The BioID system supports the enrolments of client fingerprints together with the basic profile 
information. The system also offer search functionality through biometric fingerprint verification. 
When performing a search, if a client if found with any of the fingerprints submitted the system 
responds with basic profile of matched client.

## Architecture

The system has been built on a microservice architecture for scalability, resiliency, and high-availability 
requirements.
> Since functionalities are isolated in dedicated services, each service can be efficiently scaled up in 
> isolation of the others.
> 
{style="note"}

## Services
The BioID system offers two primary services: `Enrolment` and `Search`.

## Glossary

A definition list or a glossary:

Enrolment
: This is the registration of client's fingerprint together with their fingerprint images.

Search
: This is the verification of clients details using their biometric fingerprints.

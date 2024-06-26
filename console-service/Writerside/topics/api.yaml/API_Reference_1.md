# Enrolment Service API Reference

The `Enrolment service` supports the registration of clients (their basic demographic information) 
together with their biometric fingerprints. When a client is registered in the system, that client 
is then referred to as a `global client` - whose identity can be verified and resolved centrally 
on behalf of different source systems, `potentially inhibiting irreconcilable duplicates`. 
The information captured during registration is used to uniquely identify clients across systems.

The BioID binds `Auxiliary IDs` (a collection of unique identifiers from different source systems) to a 
registered client which can be used to associate extra information to a global client within the source systems.

Operations available:

Enroll new client
: Enrol new client in the BioID system.

Get enrolled client
: Retrieve the full record of the enrolled client.

Update auxiliary IDs
: Update the auxiliary IDs previously attached to the client.
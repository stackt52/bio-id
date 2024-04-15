## Search Service

The `Search-service` exposes endpoints for searching for a client given a fingerprint

## Features

1. **Fingerprint Verification:** It utilises the [sourceAFIS](https://sourceafis.machinezoo.com/java) library to validate the existence of a client's 
fingerprint information by cross-referencing it with data stored in the bio-data-service.
2. **Client Identification:** It employs the client's Identifiers to query the enrolment service.

## Service Dependencies

- **Bio-data-service:** Provides access to fingerprint data for comparison.
- **Enrolment-service:** Facilitates the retrieval of patient profiles based on client identification.